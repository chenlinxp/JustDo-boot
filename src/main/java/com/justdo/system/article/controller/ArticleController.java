package com.justdo.system.article.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.article.domain.ArticleDO;
import com.justdo.system.article.service.ArticleService;
import com.justdo.system.dict.service.DictContentService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章内容
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-28 23:27:31
 */
 
@Controller
@RequestMapping("/system/article")
public class ArticleController {

	private String preUrl="system/article"  ;
	@Autowired
	private ArticleService articleService;

	@Autowired
	DictContentService dictContentService;

	/**
	* 文章内容列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:article:list")
	@ApiOperation(value="返回文章内容列表界面", notes="返回文章内容列表界面")
	String Article(){
	    return preUrl + "/article";
	}


	/**
	* 文章内容列表数据
	* @param params
	* @return
	*/
	@Log("文章内容列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:article:list")
	@ApiOperation(value="获取文章内容列表接口", notes="获取文章内容列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArticleDO> articleList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(articleList, total);
		return pageUtils;
	}

	/**
	* 文章内容详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:article:view")
	@ApiOperation(value="返回文章内容详情页面", notes="返回文章内容详情页面")
	String view(@PathVariable("id") String id,Model model){
			ArticleDO article = articleService.get(id);
		model.addAttribute("article", article);
		return preUrl + "/view";
	}

	/**
	 * 文章内容详情接口
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("system:article:view")
	@ApiOperation(value="返回文章内容详情", notes="返回文章内容详情")
	ArticleDO info(@PathVariable("id") String id){
			ArticleDO article = articleService.get(id);
		return article;
	}

	/**
	* 文章内容增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:article:add")
	@ApiOperation(value="返回文章内容增加页面", notes="返回文章内容增加页面")
	String add(Model model){
		return preUrl + "/add";
	}


	/**
	 * 保存文章内容
	 * @param article
     * @return R
	 */
	@Log("文章内容保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:article:add")
	@ApiOperation(value="保存文章内容接口", notes="保存文章内容接口")
	public R save( ArticleDO article){
		Date date = new Date();
		article.setCreateTime(date);
		article.setModifyTime(date);
		int count;
		if (StringUtils.isNotEmpty(article.getArticleId())) {
			count = articleService.save(article);
		} else {
			count = articleService.update(article);
		}
		if(count>0){
			return R.ok().put("articleId", article.getArticleId());
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑文章内容页面
    * @param articleId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{articleId}")
	@RequiresPermissions("system:article:edit")
	@ApiOperation(value="返回文章内容编辑页面", notes="返回文章内容编辑页面")
	String edit(@PathVariable("articleId") String articleId,Model model){
		ArticleDO article = articleService.get(articleId);
		model.addAttribute("article", article);
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新文章内容
	 * @param article
	 * @return R
	 */
	@Log("文章内容更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:article:edit")
	@ApiOperation(value="更新文章内容接口", notes="更新文章内容接口")
	public R update( ArticleDO article){
		Date date = new Date();
		article.setModifyTime(date);
		if(articleService.update(article)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除文章内容
	 * @param articleId
	 * @return R
	 */
	@Log("文章内容删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:article:del")
	@ApiOperation(value="删除文章内容接口", notes="删除文章内容接口")
	public R remove( String articleId){
		if(articleService.del(articleId)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除文章内容
	 * @param articleIds
	 * @return R
	 */
	@Log("文章内容批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:article:batchDel")
	@ApiOperation(value="批量删除文章内容接口", notes="批量删除文章内容接口")
	public R remove(@RequestParam("ids[]") String[] articleIds){
		if(articleService.batchDel(articleIds)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}
}
