package com.codefog.admin.admin.modular.system.controller;

import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.annotion.core.Permission;
import com.codefog.admin.bean.constant.Const;
import com.codefog.admin.bean.dictmap.RoleDict;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.admin.core.base.controller.BaseController;
import com.codefog.admin.admin.core.base.tips.Tip;
import com.codefog.admin.admin.core.cache.CacheKit;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.service.system.UserService;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.warpper.RoleWarpper;
import com.codefog.admin.bean.vo.node.ZTreeNode;
import com.codefog.admin.bean.constant.cache.Cache;
import com.codefog.admin.bean.entity.system.Role;
import com.codefog.admin.bean.entity.system.User;
import com.codefog.admin.dao.system.RoleRepository;
import com.codefog.admin.dao.system.UserRepository;
import com.codefog.admin.service.system.LogObjectHolder;
import com.codefog.admin.service.system.RoleService;
import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.Convert;
import com.codefog.admin.utils.ToolUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static String PREFIX = "/system/role";

    @Resource
    UserRepository userRepository;

    @Resource
    RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = roleService.get(roleId);
        model.addAttribute(role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return PREFIX + "/role_assign.html";
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String roleName) {
        List roles = null;
        if(Strings.isNullOrEmpty(roleName)) {
            roles = (List) roleRepository.findAll();
        }else{
            roles = roleRepository.findByName(roleName);
        }
        return super.warpObject(new RoleWarpper(BeanUtil.objectsToMaps(roles)));
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setId(null);
        roleRepository.save(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改角色", key = "name", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip edit(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleRepository.save(role);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除角色", key = "roleId", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip remove(@RequestParam Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        this.roleService.delRoleById(roleId);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     */
    @RequestMapping(value = "/view/{roleId}")
    @ResponseBody
    public Tip view(@PathVariable Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
       roleService.get(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Long roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        roleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList =  roleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Long userId) {
        User theUser = userService.get(userId);
        String roleid = theUser.getRoleid();
        if (ToolUtil.isEmpty(roleid)) {
            List<ZTreeNode> roleTreeList = roleService.roleTreeList();
            return roleTreeList;
        } else {
            Long[] roleIds = Convert.toLongArray(",", roleid);
            List<ZTreeNode> roleTreeListByUserId = this.roleService.roleTreeListByRoleId(roleIds);
            return roleTreeListByUserId;
        }
    }

}
