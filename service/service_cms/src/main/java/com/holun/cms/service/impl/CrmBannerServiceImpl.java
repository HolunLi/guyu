package com.holun.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.holun.cms.entity.CrmBanner;
import com.holun.cms.mapper.CrmBannerMapper;
import com.holun.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author holun
 * @since 2021-10-07
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * @Cacheable是Spring提供的缓存注解之一，该注解一般用在查询方法上，会将该方法从数据库中查询出来的数据存进行缓存，第二次查询时，就会先从缓存中查找
     * 当该注解标记在一个类上时则表示该类所有的方法都是支持缓存的
     *
     * value属性是必须指定的，其表示当前方法的返回值是会被缓存在哪个Cache上，对应Cache的名称。
     * key属性是用来指定该方法的返回结果，对应的key（因此可以使用key属性自定义key的名称，如果没有指定该属性时，Spring将使用默认的策略生成key）
     * 该方法的返回值会作为value，与自定义的key一起构成键值对，被存放到缓存中
     */
    @Cacheable(value = "bannerIndex", key = "'bannerList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2"); //使用last方法在sql语句最后面拼接语句（工作中不建议使用）
        List<CrmBanner> crmBanners = baseMapper.selectList(null);

        return crmBanners;
    }
}
