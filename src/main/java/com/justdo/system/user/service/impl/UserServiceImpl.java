package com.justdo.system.user.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;
import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.justdo.config.JustdoConfig;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import com.justdo.common.utils.*;
import com.justdo.system.user.domain.UserVO;
import com.justdo.common.domain.Tree;
import com.justdo.system.dept.dao.DeptDao;
import com.justdo.system.user.dao.UserDao;
import com.justdo.system.user.dao.UserRoleDao;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.user.domain.UserDO;
import com.justdo.system.user.domain.UserRoleDO;
import com.justdo.system.user.service.UserService;




/**
 * 用户
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	private FileService fileDaoService;
	@Autowired
	private JustdoConfig justdoConfig;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(String id) {
		List<String> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		user.setDeptName(deptMapper.get(user.getDeptId()).getDeptname());
		user.setRoleIds(roleIds);
		return user;
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		return userMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Transactional
	@Override
	public int save(UserDO user) {
		int count = userMapper.save(user);
		String userId = user.getUserId();
		List<String> roles = user.getRoleIds();
		userRoleMapper.delByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (String roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return count;
	}

	@Override
	public int update(UserDO user) {
		int r = userMapper.update(user);
		String userId = user.getUserId();
		List<String> roles = user.getRoleIds();
		userRoleMapper.delByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		for (String roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		return r;
	}

	@Override
	public int del(String userId) {
		userRoleMapper.delByUserId(userId);
		return userMapper.del(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = userMapper.list(params).size() > 0;
		return exit;
	}

	@Override
	public Set<String> listRoles(String userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO,UserDO userDO) throws Exception {
		if(Objects.equals(userVO.getUserDO().getUserId(),userDO.getUserId())){
			if(Objects.equals(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdOld()),userDO.getPassword())){
				userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(),userVO.getPwdNew()));
				return userMapper.update(userDO);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		UserDO userDO =get(userVO.getUserDO().getUserId());
		if("admin".equals(userDO.getUsername())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
		return userMapper.update(userDO);


	}

	@Transactional
	@Override
	public int batchDel(String[] userIds) {
		int count = userMapper.batchDel(userIds);
		userRoleMapper.batchDelByUserId(userIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
		String[] pDepts = deptMapper.listParentDept();
		String[] uDepts = userMapper.listAllDept();
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
		List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
		for (UserDO user : users) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(UserDO userDO) {
		return userMapper.update(userDO);
	}

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, String userId) throws Exception {
		String fileName = file.getOriginalFilename();
		fileName = FileUtils.renameToUUID(fileName);
		FileDO fileDao = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
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
			FileUtils.uploadFile(b, justdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			throw  new Exception("图片裁剪错误！！");
		}
		Map<String, Object> result = new HashMap<>();
		if(fileDaoService.save(fileDao)>0){
			UserDO userDO = new UserDO();
			userDO.setUserId(userId);
			userDO.setPicId(fileDao.getId());
			if(userMapper.update(userDO)>0){
				result.put("url",fileDao.getUrl());
			}
		}
		return result;
    }

}
