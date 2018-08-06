package com.justdo.system.dicttype.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justdo.system.dicttype.dao.DictTypeDao;
import com.justdo.system.dicttype.domain.DictTypeDO;
import com.justdo.system.dicttype.service.DictTypeService;




/**
 * 字典索引表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-21 18:28:07
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {
    @Autowired
    private DictTypeDao dicttypeDao;

    @Override
    public DictTypeDO get(String id) {
        return dicttypeDao.get(id);
    }


    @Override
    public int save(DictTypeDO dict) {
        return dicttypeDao.save(dict);
    }

    @Override
    public int update(DictTypeDO dict) {
        return dicttypeDao.update(dict);
    }

    @Override
    public int del(String id) {
        return dicttypeDao.del(id);
    }


    @Override

    public List<DictTypeDO> list(Map<String, Object> params) {
        return dicttypeDao.list(params);
    }




}
