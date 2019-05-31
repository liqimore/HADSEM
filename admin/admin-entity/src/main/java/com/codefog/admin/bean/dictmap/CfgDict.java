package com.codefog.admin.bean.dictmap;

import com.codefog.admin.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 */
public class CfgDict extends AbstractDictMap {

    @Override
    public void init() {
        put("cfgId","参数id");
        put("cfgName","参数名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
