package com.holun.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.commonutils.Result;
import com.holun.sta.client.UcenterClient;
import com.holun.sta.entity.StatisticsDaily;
import com.holun.sta.mapper.StatisticsDailyMapper;
import com.holun.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-23
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void statisticsDailyNumber(String day) {
        System.out.println("sta" + day);
        //添加记录之前删除表相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天注册的人数
        Result registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        //登录数（模拟）
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        //视频播放数（模拟）
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        //创建课程数（模拟）
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //把获取数据添加到数据库，统计分析表里面
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister); //注册人数
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day); //统计日期

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type); //只查询想要的列
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //因为返回有两部分数据：日期 和 日期对应数量
        //前端要求数组json结构，对应后端java代码是list集合
        //创建两个list集合，一个日期list，一个数量list
        List<String> date_calculated = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        Class<StatisticsDaily> statisticsDailyClass = StatisticsDaily.class;
        Method method;
        try {
            switch (type){
                case "register_num":
                    //通过反射获取该类中指定的方法
                    method = statisticsDailyClass.getDeclaredMethod("getRegisterNum");
                    break;
                case "login_num":
                    method = statisticsDailyClass.getDeclaredMethod("getLoginNum");
                    break;
                case "video_view_num":
                    method = statisticsDailyClass.getDeclaredMethod("getVideoViewNum");
                    break;
                case "course_num":
                    method = statisticsDailyClass.getDeclaredMethod("getCourseNum");
                    break;
                default:
                    method = null;
            }

            for (StatisticsDaily statisticsDaily : staList) {
                date_calculated.add(statisticsDaily.getDateCalculated());
                numDataList.add((Integer) method.invoke(statisticsDaily));
            }

            //把封装之后两个list集合放到map集合，进行返回
            Map<String,Object> map = new HashMap<>();
            map.put("date_calculated", date_calculated);
            map.put("numDataList", numDataList);
            return map;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
