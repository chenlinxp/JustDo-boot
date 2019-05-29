package com.justdo.portal.controller;

import com.justdo.system.article.domain.ArticleDO;
import com.justdo.system.article.service.ArticleService;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
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
}
