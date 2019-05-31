package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.constant.state.BizLogType;
import com.codefog.admin.bean.entity.system.OperationLog;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.OperationLogRepository;
import com.codefog.admin.service.system.OperationLogService;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.utils.factory.Page;
import com.codefog.admin.warpper.LogWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * LogController
 *
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

    @Resource
    private OperationLogRepository operationLogRepository;
    @Autowired
    private OperationLogService operationLogService;
    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();

        page = operationLogService.getOperationLogs(page, beginTime, endTime, logName, BizLogType.valueOf(logType));
        page.setRecords((List<OperationLog>) new LogWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp());
        return Rets.success(page);
    }


    /**
     * 清空日志
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delLog() {
        operationLogRepository.clear();
        return Rets.success();
    }
}
