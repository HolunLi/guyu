package com.holun.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于在控制层和视图之间传递数据的对象
 * 在查询讲师时，用到的条件包装类
 */
@Data
public class TeacherQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称,模糊查询", example = "王")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师", example = "1")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
