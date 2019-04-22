package com.codefog.admin.api.controller.system;

import com.codefog.admin.api.controller.BaseController;
import com.codefog.admin.bean.annotion.core.BussinessLog;
import com.codefog.admin.bean.dictmap.DictMap;
import com.codefog.admin.bean.entity.system.Dict;
import com.codefog.admin.bean.enumeration.BizExceptionEnum;
import com.codefog.admin.bean.exception.GunsException;
import com.codefog.admin.bean.vo.front.Rets;
import com.codefog.admin.dao.system.DictRepository;
import com.codefog.admin.service.system.DictService;
import com.codefog.admin.utils.BeanUtil;
import com.codefog.admin.utils.StringUtils;
import com.codefog.admin.utils.ToolUtil;
import com.codefog.admin.warpper.DictWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DictController
 *
 * @author enilu
 * @version 2018/11/17 0017
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Autowired
    private DictRepository dictRepository;
    @Autowired
    private DictService dictService;

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(String name) {
        if(StringUtils.isNotEmpty(name)){
            List<Dict> list = dictRepository.findByNameLike(name);
            return Rets.success(new DictWarpper(BeanUtil.objectsToMaps(list)).warp());
        }
        List<Dict> list = dictRepository.findByPid(0L);
        return Rets.success(new DictWarpper(BeanUtil.objectsToMaps(list)).warp());
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "添加字典", key = "dictName",dict=DictMap.class)
    public Object add(String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.dictService.addDict(dictName, dictValues);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @BussinessLog(value = "修改字典", key = "dictName",dict=DictMap.class)
    public Object update(Long id,String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictName, dictValues)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.dictService.editDict(id,dictName, dictValues);
        return Rets.success();
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除字典", key = "id",dict=DictMap.class)
    public Object delete(@RequestParam Long id) {
        this.dictService.delteDict(id);
        return Rets.success();
    }

}
