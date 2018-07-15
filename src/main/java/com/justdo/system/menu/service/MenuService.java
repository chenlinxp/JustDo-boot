package com.justdo.system.menu.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.justdo.common.domain.Tree;
import com.justdo.system.menu.domain.MenuDO;

/**
 * 菜单管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface MenuService {
	Tree<MenuDO> getSysMenuTree(String id);

	List<Tree<MenuDO>> listMenuTree(String id);

	Tree<MenuDO> getTree();

	Tree<MenuDO> getTree(String id);

	List<MenuDO> list(Map<String, Object> params);

	int del(String id);

	int save(MenuDO menu);

	int update(MenuDO menu);

	MenuDO get(String id);

	Set<String> listPerms(String userId);
}
