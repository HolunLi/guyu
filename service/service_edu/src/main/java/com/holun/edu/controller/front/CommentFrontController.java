package com.holun.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.JwtUtils;
import com.holun.commonutils.Result;
import com.holun.edu.entity.EduComment;
import com.holun.edu.entity.frontvo.CommentVo;
import com.holun.edu.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "对前台课程详情页面中的评论进行管理")
@RestController
@RequestMapping("/edu/commentfront")
@CrossOrigin
public class CommentFrontController {
    @Autowired
    private EduCommentService commentService;

    @ApiOperation("根据课程id，查询该课程包含的所有评论（带分页）")
    @GetMapping("getCommentList/{page}/{limit}")
    public Result getCommentList(@ApiParam("当前页") @PathVariable Long page,
                                 @ApiParam("显示几条数据") @PathVariable Long limit,
                                 @ApiParam("课程id") String courseId) {

        Page<EduComment> commentPage = new Page<>(page, limit);
        Map<String, Object> map = commentService.getCommetListByCourseId(commentPage, courseId);

        return Result.ok().data(map);
    }

    @ApiOperation("添加评论")
    @PostMapping("addComment")
    public Result addComment(@RequestBody CommentVo commentVo, HttpServletRequest request) {
        //从请求头中获取token，再从token中获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId))
            return Result.error().message("请登录");
        commentService.addComment(commentVo, memberId);

        return Result.ok();
    }
}
