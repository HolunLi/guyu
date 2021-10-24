package com.holun.servicebase.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfo {
    //课程id
    private String id;
    //讲师姓名
    private String teacherName;
    //课程名
    private String title;
    //课程封面路径
    private String cover;
    //课程价格
    private BigDecimal price;
}
