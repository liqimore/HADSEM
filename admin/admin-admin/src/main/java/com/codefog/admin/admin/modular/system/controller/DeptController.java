package com.codefog.admin.admin.modular.system.controller;

import com.codefog.admin.admin.core.base.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.annotion.core.Permission;
import com.codefog.admin.bean.dictmap.DeptDict;
import com.codefog.admin.bean.entity.system.Dept;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.bean.vo.node.ZTreeNode;
import com.codefog.admin.dao.system.DeptRepository;
import com.codefog.admin.service.system.DeptService;
import com.codefog.admin.service.system.LogObjectHolder;
import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.utils.ToolUtil;
import com.codefog.admin.warpper.DeptWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Resource
    DeptService deptService;
    @Resource
    DeptRepository deptRepository;


    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Long deptId, Model model) {
        Dept dept = deptService.get(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptService.deptSetPids(dept);
        return this.deptRepository.save(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        List<Dept> list = this.deptService.query(condition);
        return super.warpObject(new DeptWarpper(BeanUtil.objectsToMaps(list)));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Long deptId) {
        return deptService.get(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        deptService.deptSetPids(dept);
        deptRepository.save(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long deptId) {
        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));
        deptService.deleteDept(deptId);
        return SUCCESS_TIP;
    }

}
