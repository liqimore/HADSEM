package com.codefog.admin.service.cms;

import com.codefog.admin.bean.enumeration.cms.BannerTypeEnum;
import com.codefog.admin.bean.vo.offcialSite.Banner;
import com.codefog.admin.dao.cms.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 查询首页banner数据
     * @return
     */
    public Banner queryIndexBanner(){
    return queryBanner(BannerTypeEnum.INDEX.getValue());
    }

    public Banner queryBanner(String type){
        Banner banner = new Banner();
        List<com.codefog.admin.bean.entity.cms.Banner> bannerList = bannerRepository.findAllByType(type);
        banner.setIndex(0);
        banner.setList(bannerList);
        return  banner;
    }
}
