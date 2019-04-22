package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.entity.system.LoginLog;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.LoginLogRepository;
import com.codefog.admin.service.system.LoginLogService;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.utils.factory.Page;
import com.codefog.admin.warpper.LogWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * LoginLogController
 *
 * @author enilu
 * @version 2018/10/5 0005
 */
@RestController
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    @Resource
    private LoginLogRepository loginLogRepository;
    @Autowired
    private LoginLogService loginlogService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logname) {
        Page<LoginLog> page = new PageFactory<LoginLog>().defaultPage();

        page = loginlogService.getLoginLogs(page, beginTime, endTime, logname);
        page.setRecords((List<LoginLog>) new LogWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp());
        return Rets.success(page);

    }


    /**
     * 清空日志
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delLog() {
        loginLogRepository.clear();
        return Rets.success();
    }
}
