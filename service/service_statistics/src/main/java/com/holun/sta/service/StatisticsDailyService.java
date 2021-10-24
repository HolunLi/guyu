package com.holun.sta.service;

import com.holun.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-23
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某日网站的注册人数、登录人数、播放视频数和新增课程数
    void statisticsDailyNumber(String day);

    //图表显示，返回两部分数据，日期json格式，以及数量json格式
    Map<String, Object> getShowData(String type, String begin, String end);
}
