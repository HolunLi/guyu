package com.holun.cms.service;

import com.holun.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author holun
 * @since 2021-10-07
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查询所有的banner
    List<CrmBanner> selectAllBanner();
}
