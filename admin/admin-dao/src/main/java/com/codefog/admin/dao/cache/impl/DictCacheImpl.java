package com.codefog.admin.dao.cache.impl;

import com.codefog.admin.bean.constant.cache.CacheKey;
import com.codefog.admin.bean.entity.system.Dict;
import com.codefog.admin.dao.cache.CacheDao;
import com.codefog.admin.dao.cache.DictCache;
import com.codefog.admin.dao.system.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DictCacheImpl
 *
 */
@Component
public class DictCacheImpl implements DictCache {
    @Autowired
    private DictRepository dictRepository;
    @Autowired
    private CacheDao cacheDao;

    @Override
    public List<Dict> getDictsByPname(String dictName) {
        return (List<Dict>) cacheDao.hget(EhcacheDao.CONSTANT,CacheKey.DICT+dictName,List.class);
    }

    @Override
    public String getDict(Long dictId) {
        return (String) get(CacheKey.DICT_NAME+String.valueOf(dictId));
    }

    @Override
    public void cache() {
    List<Dict> list = dictRepository.findByPid(0L);
    for(Dict dict:list){
        List<Dict> children  = dictRepository.findByPid(dict.getId());
        if(children.isEmpty()) {
           continue;
        }
        set(String.valueOf(dict.getId()), children);
        set(dict.getName(), children);
        for(Dict child:children){
            set(CacheKey.DICT_NAME+String.valueOf(child.getId()),child.getName());
        }

    }

    }

    @Override
    public Object get(String key) {
        return cacheDao.hget(EhcacheDao.CONSTANT,CacheKey.DICT+key);
    }

    @Override
    public void set(String key, Object val) {
        cacheDao.hset(EhcacheDao.CONSTANT,CacheKey.DICT+key,val);

    }
}
