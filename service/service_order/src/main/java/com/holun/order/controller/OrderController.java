package com.holun.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.commonutils.Result;
import com.holun.order.entity.Order;
import com.holun.order.entity.vo.OrderQuery;
import com.holun.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-14
 */
@Api(tags = "订单管理（后台）")
@RestController
@RequestMapping("/order/admin")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "按条件对订单进行分页查询")
    @PostMapping("pageOrderCondition/{current}/{limit}")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "第几页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "显示几条数据", required = true) @PathVariable Long limit,
                                       //使用requestBody注解，前端可以以json格式传递数据，且封装到teachQuery对象中
                                       @RequestBody(required = false) OrderQuery orderQuery) {

        Page<Order> pageCondition = new Page<>(current, limit);
        Map<String, Object> map = orderService.pageOrderCondition(pageCondition, orderQuery);

        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据订单编号，删除订单")
    @DeleteMapping("deleteOrder/{orderNo}")
    public Result deleteOrder(@PathVariable String orderNo) {
        int count = orderService.deleteOrderByOrderNo(orderNo);

        return count > 0? Result.ok() : Result.error().message("删除订单失败");
    }
}
