package com.holun.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.edu.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author holun
 * @since 2021-09-24
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //添加讲师
    int addTeacher(EduTeacher eduTeacher);

    //根据id，删除讲师
    int deleteTeacherById(String id);

    //根据id，修改讲师
    int updateTeacherById(EduTeacher eduTeacher);

    //查询前4个讲师
    List<EduTeacher> queryCourseLimit();

    //按条件对讲师进行分页查询
    Map<String, Object> pageTeacherCondition(Page<EduTeacher> pageCondition, TeacherQuery teacherQuery);

    //分页查询讲师的方法（前台）
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

    //查询讲师基本信息以及讲师讲授的课程（前台）
    Map<String, Object> getTeacherInfoAndCourseList(String teacherId);
}
