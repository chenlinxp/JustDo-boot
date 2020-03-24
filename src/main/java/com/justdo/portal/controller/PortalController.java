package com.justdo.portal.controller;

import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import com.justdo.appmanage.appversion.dao.AppVersionDao;
import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.system.article.domain.ArticleDO;
import com.justdo.system.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author justdo
 */
@RequestMapping("/portal")
@Controller
public class PortalController {
	@Autowired
	ArticleService articleService;

	@Autowired
	private AppService appService;

	@Autowired
	private AppVersionDao appVersionDao;

	@GetMapping()
	String portal() {
		return "portal/index/main";
	}

	@ResponseBody
	@GetMapping("/open/list")
	public PageUtils opentList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<ArticleDO> articleList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(articleList, total);
		return pageUtils;
	}

	@GetMapping("/open/post/{articleId}")
	String post(@PathVariable("articleId") String articleId, Model model) {
		ArticleDO articleDO = articleService.get(articleId);
		model.addAttribute("article", articleDO);
		model.addAttribute("modifytime", DateUtils.format(articleDO.getModifyTime()));
		return "portal/index/post";
	}
	@GetMapping("/open/page/{categories}")
	String about(@PathVariable("categories") String categories, Model model) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("categories", categories);
		ArticleDO articleDO =null;
		if(articleService.list(map).size()>0){
			articleDO = articleService.list(map).get(0);
		}
		model.addAttribute("article", articleDO);
		return "portal/index/post";
	}

	@GetMapping("/app/{appId}")
	String appInfo(@PathVariable("appId") String appId,@RequestParam(value="id",defaultValue="") String appVersionId, Model model) {

		AppDO appDO = appService.get(appId);
		AppVersionDO appVersionDO = null;
		Map<String, Object> map = new HashMap<>(16);
		map.put("appId", appId);
		if(appVersionId==""){
			List<AppVersionDO> appVersionDOList = appVersionDao.list(map);
			model.addAttribute("appVersionDOList", appVersionDOList);
		}
		else{
			map.put("appVersionId", appVersionId);
			if(appVersionDao.list(map).size()>0){
				appVersionDO = appVersionDao.list(map).get(0);
			}
			model.addAttribute("appVersionDO", appVersionDO);
		}
		model.addAttribute("appDO", appDO);

		return "portal/app";
	}
}