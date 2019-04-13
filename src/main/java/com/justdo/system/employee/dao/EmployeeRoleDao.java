package com.justdo.system.employee.dao;

import com.justdo.system.employee.domain.EmployeeRoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 员工与角色对应关系
 * 
 * @author chenlin
 * @email
 * @date 2017-10-03 11:08:59
 */
@Mapper
public interface EmployeeRoleDao {

	EmployeeRoleDO get(String id);

	List<EmployeeRoleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(EmployeeRoleDO employeeRoleDO);

	int update(EmployeeRoleDO employeeRoleDO);

	int del(String id);

	int batchDel(String[] ids);

	List<String> listRoleId(String employeeId);

	int delByemployeeId(String employeeId);

	int delByRoleId(String roleId);

	int batchSave(List<EmployeeRoleDO> list);

	int batchDelByEmployeeId(String[] ids);
}
