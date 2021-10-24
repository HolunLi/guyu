package com.holun.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 该类中的属性要与上传的excel表的表头字段一一对应
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)  //对应excel中表头的第一个字段
    private String oneSubjectName; //一级课程
    @ExcelProperty(index = 1)  //对应excel中表头的第二个字段
    private String twoSubjectName; //二级课程
}
