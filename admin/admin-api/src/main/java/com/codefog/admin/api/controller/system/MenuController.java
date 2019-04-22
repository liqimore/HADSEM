package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.constant.state.MenuStatus;
import com.codefog.admin.bean.dictmap.MenuDict;
import com.codefog.admin.bean.entity.system.Menu;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.bean.vo.node.MenuNode;
import com.codefog.admin.bean.vo.node.Node;
import com.codefog.admin.bean.vo.node.ZTreeNode;
import com.codefog.admin.dao.system.MenuRepository;
import com.codefog.admin.service.system.LogObjectHolder;
import com.codefog.admin.service.system.MenuService;
import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.Maps;
import com.codefog.admin.utils.ToolUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MenuController
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        List<MenuNode> list = menuService.getMenus();
        return Rets.success(list);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑菜单", key = "name", dict = MenuDict.class)
    public Object save(@ModelAttribute Menu menu) {
        //判断是否存在该编号
        if(menu.getId()==null) {
            String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
            if (ToolUtil.isNotEmpty(existedMenuName)) {
                throw new GunsException(BizExceptionEnum.EXISTED_THE_MENU);
            }
            menu.setStatus(MenuStatus.ENABLE.getCode());
        }

        //设置父级菜单编号
        menuService.menuSetPcode(menu);
        menuRepository.save(menu);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除菜单", key = "id", dict = MenuDict.class)
    public Object remove(Long id) {
        logger.info("id:{}", id);
        if (ToolUtil.isEmpty(id)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(id));
        menuService.delMenuContainSubMenus(id);
        return Rets.success();
    }

    /**
     * 获取菜单树
     */
    @RequestMapping(value = "/menuTreeListByRoleId", method = RequestMethod.GET)
    public Object menuTreeListByRoleId(Integer roleId) {
        List<Long> menuIds = this.menuRepository.getMenuIdsByRoleId(roleId);
        List<ZTreeNode> roleTreeList = null;
        if (ToolUtil.isEmpty(menuIds)) {
            roleTreeList = this.menuService.menuTreeList();
        } else {
            roleTreeList = this.menuService.menuTreeListByMenuIds(menuIds);

        }
        List<Node> list = menuService.generateMenuTreeForRole(roleTreeList);
        List<Long> checkedIds = Lists.newArrayList();
        for (ZTreeNode zTreeNode : roleTreeList) {
            if (zTreeNode.getChecked() != null && zTreeNode.getChecked()) {
                checkedIds.add(zTreeNode.getId());
            }
        }
        return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    }
}
