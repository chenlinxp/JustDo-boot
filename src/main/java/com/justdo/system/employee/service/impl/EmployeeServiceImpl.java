package com.justdo.system.employee.service.impl;


import com.justdo.common.domain.Tree;
import com.justdo.common.utils.*;
import com.justdo.config.JustdoConfig;
import com.justdo.system.dept.dao.DeptDao;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.dao.EmployeeRoleDao;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.domain.EmployeeRoleDO;
import com.justdo.system.employee.domain.EmployeeVO;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import com.justdo.system.organ.dao.OrganDao;
import com.justdo.system.organ.domain.OrganDO;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;


@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private EmployeeRoleDao employeeRoleDao;

	@Autowired
	private FileService fileService;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private OrganDao organDao;

	@Autowired
	private JustdoConfig justdoConfig;
	
	@Override
	public EmployeeDO get(String  employeeId){
		String roleId = employeeRoleDao.getRoleId(employeeId);
		EmployeeDO employeeDO = employeeDao.get(employeeId);
		employeeDO.setRoleId(roleId);

		if(StringUtils.isNotEmpty(employeeDO.getOrganId())){
			OrganDO organDO = organDao.get(employeeDO.getOrganId());
			if(organDO!=null){
				employeeDO.setOrganName(organDO.getOrganname());
				organDO = null;
			}
		}

		if(StringUtils.isNotEmpty(employeeDO.getDeptmentId())){
			DeptDO deptDO = deptDao.get(employeeDO.getDeptmentId());
			if(deptDO!=null){
				employeeDO.setDeptmentName(deptDO.getDeptname());
				deptDO = null;
			}
		}


		return employeeDO;
	}

	@Override
	public String getPasswordSalt(String loginName){
		return employeeDao.getPasswordSalt(loginName);
	}
	@Override
	public List<EmployeeDO> list(Map<String, Object> map){
		return employeeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return employeeDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(EmployeeDO employee){
		String employeeId = StringUtils.getUUID();
		employee.setEmployeeId(employeeId);

		if (StringUtils.isNotEmpty(employee.getRoleId())) {
			EmployeeRoleDO employeeRoleDO = new EmployeeRoleDO();
			employeeRoleDO.setEmployeeId(employeeId);
			employeeRoleDO.setRoleId(employee.getRoleId());
			employeeRoleDao.save(employeeRoleDO);
		}
		return employeeDao.save(employee);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(EmployeeDO employee){
		String employeeId = employee.getEmployeeId();
		if(StringUtils.isNotEmpty(employee.getRoleId())){
			employeeRoleDao.delByEmployeeId(employeeId);
				EmployeeRoleDO employeeRoleDO = new EmployeeRoleDO();
				employeeRoleDO.setEmployeeId(employeeId);
				employeeRoleDO.setRoleId(employee.getRoleId());
				employeeRoleDao.save(employeeRoleDO);
		}
		return employeeDao.update(employee);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String employeeId){
		return employeeDao.del(employeeId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] employeeIds){
		return employeeDao.batchDel(employeeIds);
	}

	@Override
	public boolean exist(Map<String, Object> params) {
		boolean exist;
		exist = employeeDao.list(params).size() > 0;
		return exist;
	}

	@Override
	public List<String> listRoleIds(String employeeId) {
		return employeeRoleDao.listRoleIds(employeeId);
	}

	@Override
	public String getRoleId(String employeeId) {
		return employeeRoleDao.getRoleId(employeeId);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int resetPwd(EmployeeVO employeeVO,String employeeId) throws Exception {

		if(Objects.equals(employeeVO.getEmployeeDO().getEmployeeId(),employeeId)){
			EmployeeDO currentEmployee =  get(employeeId);
			if(Objects.equals(MD5Utils.encrypt(currentEmployee.getPasswordSalt(),employeeVO.getPwdOld()),currentEmployee.getPassword())){
				currentEmployee.setPassword(MD5Utils.encrypt(currentEmployee.getPasswordSalt(),employeeVO.getPwdNew()));
				return employeeDao.update(currentEmployee);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int adminResetPwd(EmployeeVO employeeVO) throws Exception {
		EmployeeDO employeeDO = get(employeeVO.getEmployeeDO().getEmployeeId());
		if("admin".equals(employeeDO.getLoginName())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		employeeDO.setPasswordSalt(StringUtils.getUUID());
		employeeDO.setPassword(MD5Utils.encrypt(employeeDO.getPasswordSalt(), employeeVO.getPwdNew()));
		return employeeDao.update(employeeDO);


	}


	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchRemove(String[] employeeIds) {
		int count = employeeDao.batchDel(employeeIds);
		employeeRoleDao.batchDelByEmployeeId(employeeIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptDao.list(new HashMap<String, Object>(1));
		String[] pDepts = deptDao.listParentDept();
		String[] uDepts = employeeDao.listAllDept();
		String[] allDepts = (String[]) ArrayUtils.addAll(pDepts, uDepts);
		for (DeptDO dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptid())) {
				continue;
			}
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptid().toString());
			tree.setParentId(dept.getDeptpid().toString());
			tree.setText(dept.getDeptname());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<EmployeeDO> employees = employeeDao.list(new HashMap<String, Object>(16));
		for (EmployeeDO employee : employees) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(employee.getEmployeeId().toString());
			tree.setParentId(employee.getDeptmentId());
			tree.setText(employee.getRealName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "employee");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int updatePersonal(EmployeeDO employeeDO) {
		return employeeDao.update(employeeDO);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, String employeeId) throws Exception {
		String fileName = file.getOriginalFilename();
		fileName = FileUtils.renameToUUID(fileName);
		//FileDO fileDO = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
		//获取图片后缀
		String prefix = fileName.substring((fileName.lastIndexOf(".")+1));
		String[] str=avatar_data.split(",");
		//获取截取的x坐标
		int x = (int)Math.floor(Double.parseDouble(str[0].split(":")[1]));
		//获取截取的y坐标
		int y = (int)Math.floor(Double.parseDouble(str[1].split(":")[1]));
		//获取截取的高度
		int h = (int)Math.floor(Double.parseDouble(str[2].split(":")[1]));
		//获取截取的宽度
		int w = (int)Math.floor(Double.parseDouble(str[3].split(":")[1]));
		//获取旋转的角度
		int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
		try {
			BufferedImage cutImage = ImageUtils.cutImage(file,x,y,w,h,prefix);
			BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(rotateImage, prefix, out);
			//转换后存入数据库
			byte[] b = out.toByteArray();
			File filePath=new File(ResourceUtils.getURL("classpath:").getPath());
			if(!filePath.exists()){
				filePath=new File("");
			}
			File upload = new File(filePath.getAbsolutePath(),justdoConfig.getUploadPath());
			FileUtils.uploadFile(b,upload, fileName);
		} catch (Exception e) {
			throw  new Exception("图片裁剪错误！！");
		}
		Map<String, Object> result = new HashMap<>();
		EmployeeDO employeeDO = new EmployeeDO();
		employeeDO.setEmployeeId(employeeId);
		employeeDO.setPhotoUrl("/files/" + fileName);
		if(employeeDao.update(employeeDO)>0){
				result.put("url",employeeDO.getPhotoUrl());
		}
//		if(fileService.save(fileDO)>0){
//			EmployeeDO employeeDO = new EmployeeDO();
//			employeeDO.setEmployeeId(employeeId);
//			employeeDO.setPhotoId(fileDO.getFileId());
//			if(employeeDao.update(employeeDO)>0){
//				result.put("url",fileDO.getFileUrl());
//			}
//		}
		return result;
	}
	
}
