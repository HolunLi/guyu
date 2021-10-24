package com.holun.edu.controller;

import com.holun.commonutils.Result;
import com.holun.edu.entity.subject.OneSubject;
import com.holun.edu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-02
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类（使用easyexcel来读取上传的excel表中的数据，实现添加课程分类到对应的数据库表中)
    @ApiOperation("添加课程分类")
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return Result.ok();
    }

    @ApiOperation("查询所有的一级分类（一级分类包含的二级分类也会一起查出来）")
    @GetMapping("getAllOneSubject")
    public Result getAllOneSubject() {
        List<OneSubject> allSubject = subjectService.getAllOneSubject();
        return Result.ok().data("list", allSubject);
    }
}

