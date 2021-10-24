package com.holun.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.edu.client.UcenterClient;
import com.holun.edu.entity.EduComment;
import com.holun.edu.entity.frontvo.CommentVo;
import com.holun.edu.mapper.EduCommentMapper;
import com.holun.edu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.holun.servicebase.entity.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-13
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommetListByCourseId(Page<EduComment> commentPage, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(commentPage, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total", commentPage.getTotal());
        map.put("commentList", commentPage.getRecords());
        return map;
    }

    @Override
    public void addComment(CommentVo commentVo, String memberId) {
        //远程调用获取用户信息
        MemberInfo memberInfo = ucenterClient.getMemberInfo(memberId);

        EduComment eduComment = new EduComment();
        eduComment.setMemberId(memberId);
        eduComment.setCourseId(commentVo.getCourseId());
        eduComment.setContent(commentVo.getContent());
        eduComment.setNickname(memberInfo.getNickname());
        eduComment.setAvatar(memberInfo.getAvatar());

        baseMapper.insert(eduComment);
    }
}
