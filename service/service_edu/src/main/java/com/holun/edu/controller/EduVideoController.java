package com.holun.edu.controller;

import com.holun.commonutils.Result;
import com.holun.edu.entity.EduVideo;
import com.holun.edu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Api(tags = "小节管理（课程章节包含小节，小节中包含要播放的视频）")
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);

        return Result.ok();
    }

    @ApiOperation("根据id，删除小节（小节中包含的视频也会删除）")
    @DeleteMapping("{id}")
    public Result deleteVideo(@ApiParam("小节id") @PathVariable("id") String id){
        videoService.deleteVideoById(id);

        return Result.ok();
    }
}

