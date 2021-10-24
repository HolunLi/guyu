package com.holun.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.order.client.EduClient;
import com.holun.order.client.UcenterClient;
import com.holun.order.entity.Order;
import com.holun.order.entity.PayLog;
import com.holun.order.entity.vo.OrderQuery;
import com.holun.order.mapper.OrderMapper;
import com.holun.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.holun.order.service.PayLogService;
import com.holun.order.utils.OrderNoUtil;
import com.holun.servicebase.entity.CourseInfo;
import com.holun.servicebase.entity.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;
    @Autowired
    private PayLogService payLogService;

    @Override
    public String createOrder(String courseId, String memberId) {
        //远程调用获取课程信息
        CourseInfo courseInfo = eduClient.getCourseInfoOrder(courseId);
        //远程调用获取用户信息
        MemberInfo memberInfo = ucenterClient.getMemberInfo(memberId);

        Order order = new Order();
        //随机生成订单编号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        //设置课程id
        order.setCourseId(courseId);
        //设置课程名
        order.setCourseTitle(courseInfo.getTitle());
        //设置课程封面路径
        order.setCourseCover(courseInfo.getCover());
        //设置订单金额（也就是课程价格）
        order.setTotalFee(courseInfo.getPrice());
        //设置讲师名
        order.setTeacherName(courseInfo.getTeacherName());
        //设置用户id
        order.setMemberId(memberId);
        //设置用户昵称
        order.setNickname(memberInfo.getNickname());
        //设置用户手机号
        order.setMobile(memberInfo.getMobile());
        //订单状态（0：未支付 1：已支付）
        order.setStatus(0);
        //支付类型 ，微信1
        order.setPayType(1);
        System.out.println(order);

        baseMapper.insert(order);
        return order.getOrderNo(); //返回订单编号
    }

    @Override
    public Map<String, Object> pageOrderCondition(Page<Order> pageCondition, OrderQuery orderQuery) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        //多条件组合查询，类似于动态sql
        String orderNo = orderQuery.getOrderNo();
        Integer status = orderQuery.getStatus();
        String begin = orderQuery.getBegin();
        String end = orderQuery.getEnd();
        //判断条件是否为空，拼接条件
        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageCondition, wrapper);

        long total = pageCondition.getTotal();//获取总记录数
        List<Order> records = pageCondition.getRecords();//获取分页后的list集合
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return map;
    }

    @Override
    public int deleteOrderByOrderNo(String orderNo) {
        //删除支付记录
        QueryWrapper<PayLog> payLogWrapper = new QueryWrapper<>();
        payLogWrapper.eq("order_no", orderNo);
        payLogService.remove(payLogWrapper);

        //删除订单
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("order_no", orderNo);
        int count = baseMapper.delete(orderWrapper);

        return count;
    }
}
