package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.dictmap.CfgDict;
import com.codefog.admin.bean.entity.system.Cfg;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.CfgRepository;
import com.codefog.admin.service.system.CfgService;
import com.codefog.admin.utils.Maps;
import com.codefog.admin.utils.ToolUtil;
import com.codefog.admin.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CfgController
 *
 */
@RestController
@RequestMapping("/cfg")
public class CfgController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CfgService cfgService;
    @Autowired
    private CfgRepository cfgRepository;

    /**
     * 查询操作日志列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        Page<Cfg> page = new PageFactory<Cfg>().defaultPage();

        page = cfgService.findPage(page, Maps.newHashMap("cfgName",cfgName,"cfgValue",cfgValue));
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑参数", key = "cfgName",dict= CfgDict.class)
    public Object save(@ModelAttribute Cfg cfg){
        if (ToolUtil.isOneEmpty(cfg, cfg.getCfgName(),cfg.getCfgValue())) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        if(cfg.getId()!=null){
            Cfg old = cfgService.get(cfg.getId());
            old.setCfgName(cfg.getCfgName());
            old.setCfgValue(cfg.getCfgValue());
            old.setCfgDesc(cfg.getCfgDesc());
            cfgService.save(old);
        }else {
            cfgService.save(cfg);
        }
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除参数", key = "id",dict= CfgDict.class)
    public Object remove(Long id){
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        cfgService.delete(id);
        return Rets.success();
    }
}
