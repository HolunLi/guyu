package com.holun.ucenter.mapper;

import com.holun.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author holun
 * @since 2021-10-10
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询某一天注册的人数
    Integer countRegisterDay(String day);
}
