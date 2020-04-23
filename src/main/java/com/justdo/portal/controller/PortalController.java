package com.justdo.portal.controller;

import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import com.justdo.appmanage.appversion.dao.AppVersionDao;
import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.StringUtils;
import com.justdo.config.JustdoConfig;
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

	@Autowired
	private JustdoConfig justdoConfig;

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
		ArticleDO articleDO = null;
		if (articleService.list(map).size() > 0) {
			articleDO = articleService.list(map).get(0);
		}
		model.addAttribute("article", articleDO);
		return "portal/index/post";
	}

	@GetMapping("/app/{appId}")
	String appInfo(@PathVariable("appId") String appId, @RequestParam(value = "id", defaultValue = "") String appVersionId, Model model) {

		AppDO appDO1 = null;
		AppDO appDO2 = null;

		AppVersionDO appVersionDO1 = null;
		AppVersionDO appVersionDO2 = null;


		List<AppVersionDO> appVersionDOList1 = null;

		List<AppVersionDO> appVersionDOList2 = null;

		String combineAppId = "";

		AppDO appDO = appService.get(appId);

		String appName = "";

		String iconUrl = "";

		String versionCode = "";

		String buildCode = "";

		String appSizes = "";

		String codeQrA = "";

		String modifyTime = "";

		List<AppVersionDO> appVersionDOList = null;
		if (appDO != null) {

			appName = appDO.getAppName();
			iconUrl = appDO.getIconUrl();
			codeQrA = appDO.getCodeQrA();
			modifyTime = DateUtils.format(appDO.getModifyTime(), "yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map = new HashMap<>(16);
			map.put("appId", appId);
			map.put("delFlag", "0");
			map.put("sort", "CREATE_TIME");
			map.put("order", "desc");
			appVersionDOList = appVersionDao.list(map);
			if (appDO.getAppType() == 1) {
				appDO1 = appDO;
				if (!StringUtils.isNotEmpty(appVersionId)) {
					if (appVersionDOList.size() > 0) {
						appVersionDO1 = appVersionDOList.get(0);
						appVersionDOList1 = appVersionDOList;
						appVersionDOList1.remove(0);

					}
				} else {
					map.put("appVersionId", appVersionId);
					if (appVersionDao.list(map).size() > 0) {
						appVersionDO1 = appVersionDao.list(map).get(0);
						appVersionDOList.remove(appVersionDO1);
						appVersionDOList2 = appVersionDOList;
					}

				}
				versionCode = appVersionDO1.getVersionCode();
				buildCode = appVersionDO1.getBuildCode();
				appSizes = appVersionDO1.getAppSizes();

			} else {
				appDO2 = appDO;
				if (!StringUtils.isNotEmpty(appVersionId)) {
					if (appVersionDOList.size() > 0) {
						appVersionDO2 = appVersionDOList.get(0);
						appVersionDOList2 = appVersionDOList;
						appVersionDOList2.remove(0);
					}
				} else {
					map.put("appVersionId", appVersionId);
					if (appVersionDao.list(map).size() > 0) {
						appVersionDO2 = appVersionDao.list(map).get(0);
						appVersionDOList.remove(appVersionDO2);
						appVersionDOList2 = appVersionDOList;
					}

				}
				versionCode = appVersionDO2.getVersionCode();
				buildCode = appVersionDO2.getBuildCode();
				appSizes = appVersionDO2.getAppSizes();
			}

			combineAppId = appDO.getCombineAppId();
			if (appDO.getIsCombine() == 1 && StringUtils.isNotEmpty(combineAppId)) {
				appDO = appService.get(combineAppId);
			}
			if (appDO != null) {
				map.remove("appId");
				map.put("appId", appDO.getAppId());
				if (appDO.getAppType() == 1 && appDO1 == null) {
					appDO1 = appDO;
					appVersionDOList1 = appVersionDao.list(map);
					appVersionDO1 = appVersionDOList1.get(0);
					appVersionDOList1.remove(0);
				}

				if (appDO.getAppType() == 2 && appDO2 == null) {
					appDO2 = appDO;
					appVersionDOList2 = appVersionDao.list(map);
					appVersionDO2 = appVersionDOList2.get(0);
					appVersionDOList2.remove(0);
				}
			}

		}

		model.addAttribute("appName", appName);
		model.addAttribute("iconUrl", iconUrl);
		model.addAttribute("codeQrA", codeQrA);

		model.addAttribute("modifyTime", modifyTime);

		model.addAttribute("versionCode", versionCode);
		model.addAttribute("buildCode", buildCode);
		model.addAttribute("appSizes", appSizes);

		model.addAttribute("appDO1", appDO1);
		model.addAttribute("appVersionDO1", appVersionDO1);
		model.addAttribute("appVersionDOList1", appVersionDOList1);

		model.addAttribute("appDO2", appDO2);
		model.addAttribute("appVersionDO2", appVersionDO2);
		model.addAttribute("appVersionDOList2", appVersionDOList2);

		model.addAttribute("baseAddress", justdoConfig.getBaseAddress());
		return "portal/index/app";
	}


	@GetMapping("/app/install/{appId}")
	String installUrl(@PathVariable("appId") String appId) {

		String versionId = appId;

		String url = "/error/404";
        AppVersionDO appVersionDO = appVersionDao.get(versionId);


        if(appVersionDO!=null) {

	        url = appVersionDO.getDownloadUrl();

	        if(url.endsWith(".apk")){

		        return "redirect:"+justdoConfig.getBaseAddress()+url;
	        }

	        if(url.endsWith(".plist")){

	        	return "redirect:itms-services://?action=download-manifest&url="+justdoConfig.getBaseAddress()+url;
	        }
        }
		return "redirect:"+url;
	}
}