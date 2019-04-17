package com.justdo.system.employee.service;

import com.justdo.common.domain.Tree;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.domain.EmployeeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 员工管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:19:10
 */
public interface EmployeeService {

	/**
	 * 返回实体
	 * @param employeeId
	 * @return EmployeeDO
	 */
	EmployeeDO get(String employeeId);

	/**
	 * 返回密码盐
	 * @param loginName
	 * @return passwordSalt
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
	 *
	 * @param params
	 * @return
	 */
	boolean exit(Map<String, Object> params);

	/**
	 *
	 * @param employeeId
	 * @return
	 */
	Set<String> listRoles(String employeeId);

	/**
	 *
	 * @param employeeVO
	 * @param employeeDO
	 * @return
	 * @throws Exception
	 */
	int resetPwd(EmployeeVO employeeVO, EmployeeDO employeeDO) throws Exception;

	/**
	 * 管理员重置密码
	 * @param employeeVO
	 * @return
	 * @throws Exception
	 */
	int adminResetPwd(EmployeeVO employeeVO) throws Exception;

	/**
	 *
	 * @param employeeIds
	 * @return
	 */
	int batchRemove(String[] employeeIds);
	/**
	 * 部门树
	 * @return
	 */
	Tree<DeptDO> getTree();

	/**
	 * 更新个人信息
	 * @param employeeDO
	 * @return
	 */
	int updatePersonal(EmployeeDO employeeDO);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param employeeId 员工ID
	 * @throws Exception
	 */
	Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, String employeeId) throws Exception;


}
