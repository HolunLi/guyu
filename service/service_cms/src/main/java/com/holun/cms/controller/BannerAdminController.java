package com.holun.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.holun.cms.entity.CrmBanner;
import com.holun.cms.service.CrmBannerService;
import com.holun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author holun
 * @since 2021-10-07
 */
@Api(tags = "banner后台管理")
@RestController
@RequestMapping("/cms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@ApiParam("当前页") @PathVariable long page, @ApiParam("每页显示几条数据") @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);

        return Result.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);

        return Result.ok();
    }

    @ApiOperation("修改banner")
    @PostMapping("update")
    public Result updateById(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);

        return Result.ok();
    }

    @ApiOperation("根据id查询banner")
    @GetMapping("get/{id}")
    public Result get(@ApiParam("bannerId") @PathVariable String id){
        CrmBanner banner = bannerService.getById(id);

        return Result.ok().data("item", banner);
    }

    @ApiOperation("删除banner")
    @DeleteMapping("delete/{id}")
    public Result delete(@ApiParam("bannerId") @PathVariable String id){
        bannerService.removeById(id);

        return Result.ok();
    }
}

