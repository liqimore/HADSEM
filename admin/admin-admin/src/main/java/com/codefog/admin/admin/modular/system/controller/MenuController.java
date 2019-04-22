package com.codefog.admin.admin.modular.system.controller;

import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.annotion.core.Permission;
import com.codefog.admin.bean.constant.Const;
import com.codefog.admin.bean.dictmap.MenuDict;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.admin.core.base.controller.BaseController;
import com.codefog.admin.admin.core.base.tips.Tip;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.admin.core.support.BeanKit;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.warpper.MenuWarpper;
import com.codefog.admin.bean.vo.node.ZTreeNode;
import com.codefog.admin.bean.constant.state.MenuStatus;
import com.codefog.admin.bean.entity.system.Menu;
import com.codefog.admin.dao.system.MenuRepository;
import com.codefog.admin.service.system.LogObjectHolder;
import com.codefog.admin.service.system.MenuService;
import com.codefog.admin.service.system.impl.ConstantFactory;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuService menuService;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Long menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = menuService.get(menuId);

        //获取父级菜单的id
        Menu pMenu = this.menuRepository.findByCode(menu.getPcode());

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        }
        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(menu.getPcode()));
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return PREFIX + "menu_edit.html";
    }

    /**
     * 修该菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改菜单", key = "name", dict = MenuDict.class)
    @ResponseBody
    public Tip edit(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        menuService.menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuRepository.save(menu);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        List<Menu> menus = null;
        if(Strings.isNullOrEmpty(menuName)&&Strings.isNullOrEmpty(level)) {
            menus = (List<Menu>) this.menuRepository.findAll();
        }
        if(!Strings.isNullOrEmpty(menuName)&&!Strings.isNullOrEmpty(level)) {
            menus = this.menuRepository.findByNameLikeAndLevels("%"+menuName+"%", Integer.valueOf(level));
        }
        if(!Strings.isNullOrEmpty(menuName)&&Strings.isNullOrEmpty(level)) {
            menus = this.menuRepository.findByNameLike("%"+menuName+"%");
        }
        if(Strings.isNullOrEmpty(menuName)&&!Strings.isNullOrEmpty(level)) {
            menus = this.menuRepository.findByLevels(Integer.valueOf(level));
        }

        return super.warpObject(new MenuWarpper(BeanUtil.objectsToMaps(menus)));
    }

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @BussinessLog(value = "菜单新增", key = "name", dict = MenuDict.class)
    @ResponseBody
    public Tip add(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new GunsException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        //设置父级菜单编号
        menuService.menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuRepository.save(menu);
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(value = "删除菜单", key = "menuId", dict = MenuDict.class)
    @ResponseBody
    public Tip remove(@RequestParam Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));

        this.menuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        menuService.get(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;

    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = this.menuRepository.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = this.menuService.menuTreeListByMenuIds(menuIds);
            return roleTreeListByUserId;
        }
    }


}
