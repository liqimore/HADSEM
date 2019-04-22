package com.codefog.admin.api.controller.cms;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.dictmap.CommonDict;
import com.codefog.admin.bean.entity.cms.Channel;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.cms.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 栏目管理
 */
@RestController
@RequestMapping("/channel")
public class ChannelMgrController extends BaseController {
    @Autowired
    private ChannelRepository channelRepository;
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑栏目",key="name",dict = CommonDict.class)
    public Object save(@ModelAttribute Channel channel){
        channelRepository.save(channel);
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除栏目",key="id",dict = CommonDict.class)
    public Object remove(Long id){
        channelRepository.deleteById(id);
        return Rets.success();
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(){
        List<Channel> list = channelRepository.findAll();
        return Rets.success(list);
    }
}
