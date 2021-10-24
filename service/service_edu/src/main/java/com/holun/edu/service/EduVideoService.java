package com.holun.edu.service;

import com.holun.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-03
 */
public interface EduVideoService extends IService<EduVideo> {
    //根据课程id,删除小节（连同该小节中包含的视频也随之删除）
    void deleteVideoByCourseId(String courseId);

    //根据id，删除小节（连同该小节中包含的视频也随之删除）
    void deleteVideoById(String id);
}
