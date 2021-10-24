package com.holun.edu.mapper;

import com.holun.edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.holun.edu.entity.frontvo.CourseWebVo;
import com.holun.edu.entity.vo.CoursePublishInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id，查询课程发布信息
    CoursePublishInfoVo getCoursePublishInfo(String courseId);

    //根据课程id，查询课程基本信息（前台）
    CourseWebVo getBaseCourseInfo(String courseId);
}
