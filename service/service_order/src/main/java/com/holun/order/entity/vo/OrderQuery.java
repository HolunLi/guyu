package com.holun.order.entity.vo;

import lombok.Data;

@Data
public class OrderQuery {
    private String orderNo;
    private Integer status;
    private String begin;
    private String end;
}
