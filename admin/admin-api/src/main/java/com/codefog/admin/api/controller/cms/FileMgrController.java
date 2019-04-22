package com.codefog.admin.api.controller.cms;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.entity.system.FileInfo;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.service.cms.ContactsService;
import com.codefog.admin.service.system.FileService;
import com.codefog.admin.utils.Maps;
import com.codefog.admin.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileMgr")
public class FileMgrController extends BaseController {
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private FileService fileService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String originalFileName
                       ) {
        Page<FileInfo> page = new PageFactory<FileInfo>().defaultPage();
        page = fileService.findPage(page, Maps.newHashMap("originalFileName",originalFileName));
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }
}
