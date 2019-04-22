package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.entity.system.Notice;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.SysNoticeRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * NoticeController
 *
 * @author enilu
 * @version 2018/12/17 0017
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private SysNoticeRepository sysNoticeRepository;
    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    public Object list(String condition) {
        List<Notice> list = null;
        if(Strings.isNullOrEmpty(condition)) {
            list = (List<Notice>) this.sysNoticeRepository.findAll();
        }else{
            list = sysNoticeRepository.findByTitleLike("%"+condition+"%");
        }
        return Rets.success(list);
    }
}
