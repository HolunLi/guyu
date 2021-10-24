package com.holun.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UcenterMemberVo类用于封装手机号和密码
 */
@Data
public class LoginVo {
    @ApiModelProperty(value = "手机号", example = "18621327379")
    private String mobile;

    @ApiModelProperty(value = "密码", example = "11111")
    private String password;
}
