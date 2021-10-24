package com.holun.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.edu.entity.frontvo.CourseFrontVo;
import com.holun.edu.entity.frontvo.CourseWebVo;
import com.holun.edu.entity.vo.CourseInfoVo;
import com.holun.edu.entity.vo.CoursePublishInfoVo;
import com.holun.edu.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id, 查询课程信息
    CourseInfoVo getCourseInfoById(String courseId);

    //根据课程id, 查询课程发布信息
    CoursePublishInfoVo getCoursePublishInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id，删除课程（该课程中的章节，小节，视频会一起被删除）
    void deleteCourseById(String courseId);

    //查询前8条热门课程
    List<EduCourse> queryCourseLimit();

    //按条件对课程进行分页查询（后端）
    Map<String, Object> pageCourseCondition(Page<EduCourse> pageCondition, CourseQuery courseQuery);

    //按条件对课程进行分页查询（前台）
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    //根据课程id，查询课程基本信息（前台）
    CourseWebVo getBaseCourseInfo(String courseId);
}
