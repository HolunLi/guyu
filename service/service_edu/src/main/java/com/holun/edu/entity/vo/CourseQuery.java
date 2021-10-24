package com.holun.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于在控制层和视图之间传递数据的对象
 * 在查询课程时，用到的条件包装类
 */
@Data
public class CourseQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询", example = "Redis")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft（未发布） Normal（已发布）", example = "Draft")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
