package com.holun.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.commonutils.Result;
import com.holun.edu.client.VodClient;
import com.holun.edu.entity.EduVideo;
import com.holun.edu.mapper.EduVideoMapper;
import com.holun.edu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.holun.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id"); //只查询出video_source_id这一个列
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);

        List<String> videoIdList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId()))
                videoIdList.add(eduVideo.getVideoSourceId());
        }
        if (!videoIdList.isEmpty()) {
            //删除小节中包含的所有视频
            Result result = vodClient.deleteVideos(videoIdList);
            if (result.getCode() == 20001)
                throw new GuliException(20001,"出现熔断，删除多个视频失败");
        }

        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        //删除所有小节
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteVideoById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        //判断小节中是否包含视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            //删除小节中的视频
            Result result = vodClient.deleteVideo(videoSourceId);
            if (result.getCode() == 20001)
                throw new GuliException(20001, "出现熔断，删除视频失败");
        }

        //删除小节
        baseMapper.deleteById(id);
    }
}
