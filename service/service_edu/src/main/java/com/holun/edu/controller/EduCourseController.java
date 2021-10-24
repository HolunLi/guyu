package com.holun.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.Result;
import com.holun.edu.entity.EduCourse;
import com.holun.edu.entity.vo.CourseInfoVo;
import com.holun.edu.entity.vo.CoursePublishInfoVo;
import com.holun.edu.entity.vo.CourseQuery;
import com.holun.edu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("添加课程")
    @PostMapping("addCourserInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //这里返回添加之后的课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);

        return Result.ok().data("courseId", id);
    }

    @ApiOperation("根据课程id，删除课程")
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@ApiParam("课程id") @PathVariable String courseId) {
        courseService.deleteCourseById(courseId);

        return Result.ok();
    }

    @ApiOperation("发布课程，修改课程状态")
    @PostMapping("publishCourse/{courseId}")
    public Result publishCourse(@ApiParam("课程id") @PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);

        return Result.ok();
    }

    @ApiOperation("根据课程id, 查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@ApiParam("课程id") @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);

        return Result.ok().data("courseInfo", courseInfoVo);
    }

    @ApiOperation("根据课程id, 查询课程发布信息")
    @GetMapping("getCoursePublishInfo/{courseId}")
    public Result getCoursePublishInfo(@ApiParam("课程id") @PathVariable String courseId) {
        CoursePublishInfoVo coursePublishInfoVo = courseService. getCoursePublishInfo(courseId);

        return Result.ok().data("coursePublishInfo", coursePublishInfoVo);
    }

    @ApiOperation(value = "按条件对课程进行分页查询")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "显示几条数据", required = true) @PathVariable Long limit,
                                       //使用requestBody注解，前端可以以json格式传递数据，且封装到courseQuery对象中
                                       @RequestBody(required = false) CourseQuery courseQuery) {

        Page<EduCourse> pageCondition = new Page<>(current, limit);
        Map<String, Object> map = courseService.pageCourseCondition(pageCondition, courseQuery);

        return Result.ok().data(map);
    }
}

