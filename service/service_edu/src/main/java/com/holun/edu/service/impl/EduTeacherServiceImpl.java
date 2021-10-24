package com.holun.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.entity.EduCourse;
import com.holun.edu.entity.EduTeacher;
import com.holun.edu.entity.vo.TeacherQuery;
import com.holun.edu.mapper.EduTeacherMapper;
import com.holun.edu.service.EduCourseService;
import com.holun.edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 讲师 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-09-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Autowired
    private EduCourseService courseService;

    @CacheEvict(value = "teacherIndex", allEntries = true)
    @Override
    public int addTeacher(EduTeacher eduTeacher) {
        int count = baseMapper.insert(eduTeacher);

        return count;
    }

    @CacheEvict(value = "teacherIndex", allEntries = true)
    @Override
    public int deleteTeacherById(String id) {
        int count = baseMapper.deleteById(id);

        return count;
    }

    @CacheEvict(value = "teacherIndex", allEntries = true)
    @Override
    public int updateTeacherById(EduTeacher eduTeacher) {
        int count = baseMapper.updateById(eduTeacher);

        return count;
    }

    @Cacheable(value = "teacherIndex", key = "'teacherList'")
    @Override
    public List<EduTeacher> queryCourseLimit() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(wrapper);
        return eduTeachers;
    }

    @Override
    public Map<String, Object> pageTeacherCondition(Page<EduTeacher> pageCondition, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //多条件组合查询，类似于动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，拼接条件
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
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
        List<EduTeacher> records = pageCondition.getRecords();//获取分页后的list集合
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return map;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher, wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext(); //是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious(); //是否有上一页

        //把分页数据获取出来，放到map集合
        Map<String,Object> map = new HashMap<>();
        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public Map<String, Object> getTeacherInfoAndCourseList(String teacherId) {
        //1，根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = baseMapper.selectById(teacherId);
        //2，根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        Map<String,Object> map = new HashMap<>();
        map.put("teacher", eduTeacher);
        map.put("courseList", courseList);
        return map;
    }
}
