package com.holun.order.mapper;

import com.holun.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author holun
 * @since 2021-10-14
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
