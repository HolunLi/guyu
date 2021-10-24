package com.holun.cms.controller;

import com.holun.cms.entity.CrmBanner;
import com.holun.cms.service.CrmBannerService;
import com.holun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-07
 */
@Api(tags = "banner前台管理")
@RestController
@RequestMapping("/cms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("查询前两条banner")
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();

        return Result.ok().data("list", list);
    }
}
