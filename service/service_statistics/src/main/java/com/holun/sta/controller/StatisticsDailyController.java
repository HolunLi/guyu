package com.holun.sta.controller;

import com.holun.commonutils.Result;
import com.holun.sta.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-23
 */
@Api(tags = "统计网站日常数据")
@RestController
@RequestMapping("/sta/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("统计某日网站的注册人数、登录人数、播放视频数和新增课程数")
    @PostMapping("statisticsDailyNumber/{day}")
    public Result count(@PathVariable String day) {
        statisticsDailyService.statisticsDailyNumber(day);

        return Result.ok();
    }

    //图表显示，返回两部分数据，日期json格式，以及数量json格式
    @GetMapping("showDate/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);

        return Result.ok().data(map);
    }
}

