package com.justdo.system.article.service;

import com.justdo.system.article.domain.ArticleDO;

import java.util.List;
import java.util.Map;

/**
 * 文章内容
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-28 23:27:31
 */
public interface ArticleService {

	/**
	 * 返回实体
	 * @param articleId
	 * @return ArticleDO
	 */
		ArticleDO get(String articleId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<ArticleDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param article
	 * @return
	 */
	int save(ArticleDO article);

	/**
	 * 更新实体
	 * @param article
	 * @return list
	 */
	int update(ArticleDO article);

	/**
	 * 删除实体
	 * @param articleId
	 * @return list
	 */
	int del(String articleId);

	/**
	 * 批量删除实体
	 * @param articleIds
	 * @return list
	 */
	int batchDel(String[] articleIds);

}
