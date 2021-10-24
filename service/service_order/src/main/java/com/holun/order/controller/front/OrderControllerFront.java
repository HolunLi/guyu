package com.holun.order.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.commonutils.JwtUtils;
import com.holun.commonutils.Result;
import com.holun.order.entity.Order;
import com.holun.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-14
 */
@Api(tags = "订单管理（前台）")
@RestController
@RequestMapping("/order/front")
@CrossOrigin
public class OrderControllerFront {
    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("createOrder/{courseId}")
    public Result createOrder(@ApiParam("课程id") @PathVariable String courseId, HttpServletRequest request) {
        //从请求头中获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId))
            return Result.error().message("请登录后，再购买课程");
        String orderNo = orderService.createOrder(courseId, memberId);

        return Result.ok().data("orderNo", orderNo);
    }
    
    @ApiOperation("根据订编号，查询订单信息")
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@ApiParam("订单编号") @PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        return Result.ok().data("orderInfo", order);
    }

    @ApiOperation("根据课程id和用户id查询订单表中订单状态（查询用户是否已购买该课程）")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@ApiParam("课程id") @PathVariable(name = "courseId") String courseId, @ApiParam("用户id") @PathVariable(name = "memberId") String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);

        return count > 0;
    }
}

