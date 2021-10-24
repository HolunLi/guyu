package com.holun.sta.schedule;

import com.holun.sta.service.StatisticsDailyService;
import com.holun.sta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;

@Configuration
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //0/5* * * *？表示每隔5秒执行一次这个方法

    /**
     *     @Scheduled(cron = "0/5 * * * * ?")
     *     public void task1(){
     *         System.out.println("**********task1执行了" + new Date());
     *     }
     */

    //每天凌晨1点，记录前一天网站的注册人数、登录人数、播放视频数和新增课程数
    //crom表达式（七子表达式），秒分时日月周
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        statisticsDailyService.statisticsDailyNumber(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
