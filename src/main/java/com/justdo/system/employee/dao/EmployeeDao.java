package com.justdo.system.employee.dao;

import com.justdo.system.employee.domain.EmployeeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 员工管理
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:19:10
 */
@Mapper
public interface EmployeeDao {

	/**
	* 返回实体
	* @param employeeId
	* @return EmployeeDO
	*/
	EmployeeDO get(String employeeId);

	/**
	 * 返回密码盐
	 * @param loginName
	 * @return
	 */
	String getPasswordSalt(String loginName);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<EmployeeDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param employee
	 * @return
	 */
	int save(EmployeeDO employee);

	/**
	 * 更新实体
	 * @param employee
	 * @return list
	 */
	int update(EmployeeDO employee);

	/**
	 * 删除实体
	 * @param employeeId
	 * @return list
	 */
	int del(String employeeId);

	/**
	 * 批量删除实体
	 * @param employeeIds
	 * @return list
	 */
	int batchDel(String[] employeeIds);

	/**
	 * 获取有员工的部门ID
	 * @return
	 */
	String [] listAllDept();

	/**
	 * 获取部门下的员工数量
	 * @param deptId
	 * @return
	 */
	int getDeptEmployeeCount(String deptId);
}
