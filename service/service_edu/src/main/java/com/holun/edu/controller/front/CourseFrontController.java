package com.holun.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.JwtUtils;
import com.holun.commonutils.Result;
import com.holun.edu.client.OrderClient;
import com.holun.edu.entity.EduCourse;
import com.holun.edu.entity.chapter.ChapterVo;
import com.holun.edu.entity.frontvo.CourseFrontVo;
import com.holun.edu.entity.frontvo.CourseWebVo;
import com.holun.edu.service.EduChapterService;
import com.holun.edu.service.EduCourseService;
import com.holun.servicebase.entity.CourseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "前台课程页面管理")
@RestController
@RequestMapping("/edu/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation("按条件对课程进行分页查询")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@ApiParam(name = "page", value = "第几页", required = true) @PathVariable long page,
                                     @ApiParam(name = "limit", value = "显示几条数据", required = true) @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);

        return Result.ok().data(map);
    }

    @ApiOperation("查询课程详情信息（课程基本信息+课程包含的章节信息）")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@ApiParam("课程id") @PathVariable String courseId, HttpServletRequest request){
        //根据课程id，查询课程基本信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id，查询该课程包含的章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterByCourseId(courseId);
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断登录的用户是否已购买该课程
        boolean isBuyCourse = false;
        if (!StringUtils.isEmpty(memberId))
            isBuyCourse = orderClient.isBuyCourse(courseId, memberId);

        return Result.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuyCourse", isBuyCourse);
    }

    @ApiOperation("查询课程基本信息")
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseInfo getCourseInfoOrder(@ApiParam("课程id") @PathVariable(name = "courseId") String courseId){
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(courseWebVo, courseInfo);

        return courseInfo;
    }
}
