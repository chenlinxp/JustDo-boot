package com.justdo.system.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.justdo.system.dict.dao.DictContentDao;
import com.justdo.system.dict.domain.DictContentDO;
import com.justdo.system.dict.service.DictContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.justdo.common.utils.StringUtils;

 /**
 * 字典表
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class DictContentServiceImpl implements DictContentService {
     @Autowired
     private DictContentDao dictContentDao;

     @Override
     public DictContentDO get(String dcid){
         return dictContentDao.get(dcid);
     }

     @Override
     public List<DictContentDO> list(Map<String, Object> map){
         return dictContentDao.list(map);
     }

     @Override
     public int count(Map<String, Object> map){
         return dictContentDao.count(map);
     }

     @Override
     public int save(DictContentDO dictContent){
         return dictContentDao.save(dictContent);
     }

     @Override
     public int update(DictContentDO dictContent){
         return dictContentDao.update(dictContent);
     }

     @Override
     public int del(String dcid){
         return dictContentDao.del(dcid);
     }

     @Override
     public int batchDel(String[] dcids){
         return dictContentDao.batchDel(dcids);
     }


    @Override
    public String getName(String Dicttype, String value) {
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("did", Dicttype);
        param.put("dccode", value);
        String rString = "";
        List<DictContentDO> ListD =  dictContentDao.list(param);
        if(ListD.size()>0){
            rString = ListD.get(0).getDcvalue();
        }
        return rString;
    }

   // @Override
//    public List<DictContentDO> getHobbyList(UserDO userDO) {
//        Map<String, Object> param = new HashMap<>(16);
//        param.put("type", "hobby");
//        List<DictContentDO> hobbyList = dictContentDao.list(param);
//
//        if (StringUtils.isNotEmpty(userDO.getHobby())) {
//            String userHobbys[] = userDO.getHobby().split(";");
//            for (String userHobby : userHobbys) {
//                for (DictContentDO hobby : hobbyList) {
//                    if (!Objects.equals(userHobby, hobby..toString())) {
//                        continue;
//                    }
//                    hobby.setRemarks("true");
//                    break;
//                }
//            }
//        }
//
//        return hobbyList;
//    }

    @Override
    public List<DictContentDO> listDictByCode(String dcode) {
        return dictContentDao.listDictByCode(dcode);
    }

     @Override
     public List<DictContentDO> listByTypeId(String did)
     {
         Map<String, Object> param = new HashMap<>(16);
         param.put("did", did);
         return dictContentDao.list(param);
     }
}
