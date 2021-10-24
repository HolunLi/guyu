package com.holun.edu.controller;

import com.holun.commonutils.Result;
import com.holun.edu.entity.EduChapter;
import com.holun.edu.entity.chapter.ChapterVo;
import com.holun.edu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("查询某个课程包含的所有章节（每个章节中包含的小节也都会一起查出来）")
    @GetMapping("getChapterByCourseId/{courseId}")
    public Result getChapterByCourseId(@ApiParam("课程id") @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.getChapterByCourseId(courseId);

        return Result.ok().data("chapterList", chapterVoList);
    }

    @ApiOperation("根据章节id，查询对应的章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@ApiParam("章节id") @PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);

        return Result.ok().data("chapter", eduChapter);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);

        return Result.ok();
    }

    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter chapter){
        eduChapterService.updateById(chapter);

        return Result.ok();
    }

    @ApiOperation("根据章节id，删除章节")
    @DeleteMapping("{chapterId}")
    public Result deleteChapter(@ApiParam("章节id") @PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag)
            return Result.ok();

        return Result.error();
    }
}

