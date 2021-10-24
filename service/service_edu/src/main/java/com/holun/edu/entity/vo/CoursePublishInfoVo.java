package com.holun.edu.entity.vo;

import lombok.Data;

/**
 * 课程发布信息
 */
@Data
public class CoursePublishInfoVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
