package com.codefog.admin.service.task.job;

import com.codefog.admin.bean.entity.system.Cfg;
import com.codefog.admin.dao.system.CfgRepository;
import com.codefog.admin.service.task.JobExecuter;
import com.codefog.admin.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * HelloJob
 *
 * @author zt
 * @version 2018/12/30 0030
 */
@Component
public class HelloJob extends JobExecuter {
    @Autowired
    private CfgRepository cfgRepository;
    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {
        Cfg cfg = cfgRepository.findById(1L).get();
        cfg.setCfgDesc(cfg.getCfgDesc()+"update by "+ DateUtil.getTime());
        cfgRepository.save(cfg);
        System.out.println("hello :"+JSON.toJSONString(dataMap));
    }
}
