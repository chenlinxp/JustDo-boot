package com.justdo.system.position.dao;

import com.justdo.system.position.domain.PositionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 岗位管理
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-16 23:21:36
 */
@Mapper
public interface PositionDao {

	/**
	* 返回实体
	* @param postid
	* @return PositionDO
	*/
	PositionDO get(String postid);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<PositionDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param position
	 * @return
	 */
	int save(PositionDO position);

	/**
	 * 更新实体
	 * @param position
	 * @return list
	 */
	int update(PositionDO position);

	/**
	 * 删除实体
	 * @param postid
	 * @return list
	 */
	int del(String postid);

	/**
	 * 批量删除实体
	 * @param postids
	 * @return list
	 */
	int batchDel(String[] postids);
}
