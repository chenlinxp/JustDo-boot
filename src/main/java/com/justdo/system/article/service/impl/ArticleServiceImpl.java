package com.justdo.system.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.justdo.system.article.dao.ArticleDao;
import com.justdo.system.article.domain.ArticleDO;
import com.justdo.system.article.service.ArticleService;



@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public ArticleDO get(String articleId){
		return articleDao.get(articleId);
	}
	
	@Override
	public List<ArticleDO> list(Map<String, Object> map){
		return articleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return articleDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(ArticleDO article){
		return articleDao.save(article);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(ArticleDO article){
		return articleDao.update(article);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String articleId){
		return articleDao.del(articleId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] articleIds){
		return articleDao.batchDel(articleIds);
	}
	
}
