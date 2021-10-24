package com.holun.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.Result;
import com.holun.edu.entity.EduTeacher;
import com.holun.edu.entity.vo.TeacherQuery;
import com.holun.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-09-24
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin //解决跨域
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.addTeacher(eduTeacher) > 0? Result.ok() : Result.error();
    }

    @ApiOperation("根据讲师id，删除某个讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        return eduTeacherService.deleteTeacherById(id) > 0? Result.ok() : Result.error();
    }

    @ApiOperation("根据讲师id，修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        return eduTeacherService.updateTeacherById(eduTeacher) > 0? Result.ok() : Result.error();
    }

    @ApiOperation("根据讲师id,查询某个讲师")
    @GetMapping("queryTeacherById/{id}")
    public Result queryTeachers(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        return Result.ok().data("teacher", eduTeacher);
    }

    @ApiOperation("查询所有的讲师")
    @GetMapping("queryAllTeachers")
    public Result queryAllTeachers() {
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);

        return Result.ok().data("list", eduTeachers);
    }

    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageTeacher(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable Long current,
                              @ApiParam(name = "limit", value = "显示几条数据", required = true)@PathVariable Long limit) {
        //创建page
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//获取总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//获取分页后的list集合

        return Result.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "按条件对讲师进行分页查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "显示几条数据", required = true) @PathVariable Long limit,
                                       //使用requestBody注解，前端可以以json格式传递数据，且封装到teachQuery对象中
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageCondition = new Page<>(current, limit);
        Map<String, Object> map = eduTeacherService.pageTeacherCondition(pageCondition, teacherQuery);

        return Result.ok().data(map);
    }
}

