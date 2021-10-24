package com.holun.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.entity.EduCourse;
import com.holun.edu.entity.EduCourseDescription;
import com.holun.edu.entity.EduVideo;
import com.holun.edu.entity.frontvo.CourseFrontVo;
import com.holun.edu.entity.frontvo.CourseWebVo;
import com.holun.edu.entity.vo.CourseInfoVo;
import com.holun.edu.entity.vo.CoursePublishInfoVo;
import com.holun.edu.entity.vo.CourseQuery;
import com.holun.edu.mapper.EduCourseMapper;
import com.holun.edu.service.EduChapterService;
import com.holun.edu.service.EduCourseDescriptionService;
import com.holun.edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.holun.edu.service.EduVideoService;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    /**
     * mp底层已开启事务，所以这里无需在使用@Transactional注解
     */
    @CacheEvict(value = "courseIndex", allEntries = true)
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        //copyProperties方法，会将courseInfoVo对象中的属性值复制到eduCourse对象中对应的属性（属性名要相同）
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            throw new GuliException(20001, "添加课程失败");
        }

        //向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        //课程简介Description
        courseDescription.setDescription(courseInfoVo.getDescription());
        //获取课程id
        String cid = eduCourse.getId();
        //将课程id作为课程简介id，因为课程和课程简介是一对一的关系
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        //返回课程id
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //获取课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //获取课程描述信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public CoursePublishInfoVo getCoursePublishInfo(String courseId) {
        //baseMapper调mapper层的相关方法
        CoursePublishInfoVo coursePublishInfo = baseMapper.getCoursePublishInfo(courseId);

        return coursePublishInfo;
    }

    @CacheEvict(value = "courseIndex", allEntries = true)
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //修改课程信息
        int count = baseMapper.updateById(eduCourse);
        if (count <= 0)
            throw new GuliException(20001,"修改课程信息失败");

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        //修改课程描述信息
        courseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * @CacheEvict 是Spring提供的缓存注解之一，该注解一般用在修改和删除方法上，只要数据库中的数据发生变化，就将缓存中的数据清除（保证数据的一致性）
     * value属性指定要将哪个缓存中的数据清除
     * allEntries属性表示是否需要清除该缓存中的所有元素。默认为false，表示不需要。
     * key属性表示需要清除缓存中的哪个key（如果只是想清除缓存中的某个数据时，可以使用该属性）
     */
    @CacheEvict(value = "courseIndex", allEntries = true)
    @Override
    public void deleteCourseById(String courseId) {
        //删除小节（连同小节中的视频一起删除）
        videoService.deleteVideoByCourseId(courseId);
        //删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //删除课程描述
        courseDescriptionService.removeById(courseId);
        //删除课程
        int count = baseMapper.deleteById(courseId);
        if (count <= 0)
            throw new GuliException(20001, "删除失败");
    }

    @Cacheable(value = "courseIndex", key = "'courseList'")
    @Override
    public List<EduCourse> queryCourseLimit() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);

        return eduCourses;
    }

    @Override
    public Map<String, Object> pageCourseCondition(Page<EduCourse> pageCondition, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //多条件组合查询，类似于动态sql
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        //判断条件是否为空，拼接条件
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageCondition, wrapper);

        long total = pageCondition.getTotal();//获取总记录数
        List<EduCourse> records = pageCondition.getRecords();//获取分页后的list集合

        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return map;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){ //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){ //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        //map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
