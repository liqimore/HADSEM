package com.codefog.admin.warpper;

import com.codefog.admin.service.system.impl.ConstantFactory;
import com.codefog.admin.utils.ToolUtil;

import java.util.Map;

/**
 * 部门列表的包装
 *
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Long pid = (Long) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
