package com.codefog.admin.dao.cache;

import com.codefog.admin.bean.entity.system.Dict;

import java.util.List;

/**
 * 字典缓存
 */
public interface DictCache  extends Cache{

    List<Dict> getDictsByPname(String dictName);
    String getDict(Long dictId);
}
