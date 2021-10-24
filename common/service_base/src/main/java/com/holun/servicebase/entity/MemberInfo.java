package com.holun.servicebase.entity;

import lombok.Data;

@Data
public class MemberInfo {
    //用户id
    private String id;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //用户手机号
    private String mobile;
}
