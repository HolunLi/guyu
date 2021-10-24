package com.holun.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.edu.entity.frontvo.CommentVo;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-13
 */
public interface EduCommentService extends IService<EduComment> {

    //根据课程id，查询该课程包含的所有评论（带分页）前台
    Map<String, Object> getCommetListByCourseId(Page<EduComment> commentPage, String courseId);

    //添加评论
    void addComment(CommentVo commentVo, String memberId);
}
