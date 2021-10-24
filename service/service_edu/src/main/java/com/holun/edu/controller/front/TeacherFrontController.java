package com.holun.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.Result;
import com.holun.edu.entity.EduTeacher;
import com.holun.edu.service.EduCourseService;
import com.holun.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Api(tags = "前台讲师页面管理")
@RestController
@RequestMapping("/edu/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师的方法")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@ApiParam(name = "page", value = "第几页", required = true) @PathVariable long page,
                                      @ApiParam(name = "limit", value = "显示几条数据", required = true) @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);

        //返回分页所有数据
        return Result.ok().data(map);
    }

    @ApiOperation("讲师详情的功能")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        Map<String, Object> map = teacherService.getTeacherInfoAndCourseList(teacherId);

        return Result.ok().data(map);
    }
}
