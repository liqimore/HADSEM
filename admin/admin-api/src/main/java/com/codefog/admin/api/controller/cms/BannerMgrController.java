package com.codefog.admin.api.controller.cms;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.dictmap.CommonDict;
import com.codefog.admin.bean.entity.cms.Banner;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.cms.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * banner管理
 */
@RestController
@RequestMapping("/banner")
public class BannerMgrController extends BaseController {
    @Autowired
    private BannerRepository bannerRepository;
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑banner",key="title",dict = CommonDict.class)
    public Object save(@ModelAttribute Banner banner){
        bannerRepository.save(banner);
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除banner",key="id",dict = CommonDict.class)
    public Object remove(Long id){
        bannerRepository.deleteById(id);
        return Rets.success();
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(){
        List<Banner> list = bannerRepository.findAll();
        return Rets.success(list);
    }
}
