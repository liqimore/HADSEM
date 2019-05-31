package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.constant.Const;
import com.codefog.admin.bean.constant.factory.PageFactory;
import com.codefog.admin.bean.constant.state.ManagerStatus;
import com.codefog.admin.bean.dictmap.UserDict;
import com.codefog.admin.bean.dto.UserDto;
import com.codefog.admin.bean.entity.system.User;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.UserRepository;
import com.codefog.admin.factory.UserFactory;
import com.codefog.admin.service.system.UserService;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.utils.MD5;
import com.codefog.admin.utils.ToolUtil;
import com.codefog.admin.utils.factory.Page;
import com.codefog.admin.warpper.UserWarpper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String account,
                       @RequestParam(required = false) String name){
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        params.put("account",account);
        Page page = new PageFactory().defaultPage();
        page = userService.findPage(page, params);
        List list = (List) new UserWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp();
        page.setRecords(list);
        return Rets.success(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑管理员", key = "name", dict = UserDict.class)
    public Object save( @Valid UserDto user,BindingResult result){
        logger.info(JSON.toJSONString(user));


        if(user.getId()==null) {
            // 判断账号是否重复
            User theUser = userRepository.findByAccount(user.getAccount());
            if (theUser != null) {
                throw new GunsException(BizExceptionEnum.USER_ALREADY_REG);
            }
            // 完善账号信息
            user.setSalt(ToolUtil.getRandomString(5));
            user.setPassword(MD5.md5(user.getPassword(), user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            userRepository.save(UserFactory.createUser(user, new User()));
        }else{
            User oldUser = userService.get(user.getId());
            userRepository.save(UserFactory.updateUser(user,oldUser));
        }
        return Rets.success();
    }

    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @RequestMapping(method = RequestMethod.DELETE)
    public Object remove(Long userId){
        logger.info("id:{}",userId);
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = userService.get(userId);
        user.setStatus(ManagerStatus.DELETED.getCode());
        userRepository.save(user);
        return Rets.success();
    }
    @BussinessLog(value="设置用户角色",key="userId",dict=UserDict.class)
    @RequestMapping(value="/setRole",method = RequestMethod.GET)
    public Object setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        User user = userService.get(userId);
        user.setRoleid(roleIds);
        userRepository.save(user);
        return Rets.success();
    }

}
