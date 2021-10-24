package com.holun.edu.controller.front;

import com.holun.commonutils.Result;
import com.holun.edu.entity.EduCourse;
import com.holun.edu.entity.EduTeacher;
import com.holun.edu.service.EduCourseService;
import com.holun.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "前台首页管理")
@RestController
@RequestMapping("/edu/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("查询前8门热门课程，查询前4个讲师")
    @GetMapping("index")
    public Result index(){
        //查询前8门热门课程
        List<EduCourse> eduList = courseService.queryCourseLimit();
        //查询前4个讲师
        List<EduTeacher> teacherList = teacherService.queryCourseLimit();

        return Result.ok().data("eduList", eduList).data("teacherList", teacherList);
    }
}
