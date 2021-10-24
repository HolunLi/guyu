package com.holun.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.holun.order.entity.vo.OrderQuery;

import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-14
 */
public interface OrderService extends IService<Order> {

    //创建订单（前台）
    String createOrder(String courseId, String memberId);

    //按条件对订单进行分页查询
    Map<String, Object> pageOrderCondition(Page<Order> pageCondition, OrderQuery orderQuery);

    //根据订单编号，删除订单
    int deleteOrderByOrderNo(String orderNo);
}
