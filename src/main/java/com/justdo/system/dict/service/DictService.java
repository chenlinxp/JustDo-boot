package com.justdo.system.dict.service;

import com.justdo.system.dict.domain.DictDO;
import com.justdo.system.employee.domain.EmployeeDO;

import java.util.List;
import java.util.Map;


/**
 * 字典表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public interface DictService {

	DictDO get(Long id);

	List<DictDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DictDO dict);

	int update(DictDO dict);

	int remove(Long id);

	int batchDel(Long[] ids);

	List<DictDO> listType();


	String getName(String type, String value);

	/**
	 * 获取爱好列表
	 *
	 * @param employeeDO
	 * @return
	 */
	List<DictDO> getHobbyList(EmployeeDO employeeDO);

	/**
	 * 获取性别列表
	 *
	 * @return
	 */
	List<DictDO> getSexList();

	/**
	 * 根据type获取数据
	 *
	 * @param type
	 * @return
	 */
	List<DictDO> listByType(String type);

}
