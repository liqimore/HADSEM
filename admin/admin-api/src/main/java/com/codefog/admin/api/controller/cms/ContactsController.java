package com.codefog.admin.api.controller.cms;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.entity.cms.Contacts;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.service.cms.ContactsService;
import com.codefog.admin.utils.Maps;
import com.codefog.admin.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactsController extends BaseController {
    @Autowired
    private ContactsService contactsService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String userName,
                       @RequestParam(required = false) String mobile) {
        Page<Contacts> page = new PageFactory<Contacts>().defaultPage();
        page = contactsService.findPage(page, Maps.newHashMap("userName",userName,"mobile",mobile));
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }
}
