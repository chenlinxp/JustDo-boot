package com.justdo.system.menu.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.menu.domain.MenuDO;
/**
 * 菜单管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface MenuDao {

	MenuDO get(Long menuId);
	
	List<MenuDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MenuDO menu);
	
	int update(MenuDO menu);
	
	int remove(Long menuId);
	
	int batchDel(Long[] menuIds);
	
	List<MenuDO> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);
}
