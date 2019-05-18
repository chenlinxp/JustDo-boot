package com.justdo.appmanage.app.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
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
 * APP包管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-15 20:17:22
 */
 
@Controller
@RequestMapping("/appmanage/app")
public class AppController {

	private String preUrl="appmanage/app"  ;
	@Autowired
	private AppService appService;

	@Autowired
	DictContentService dictContentService;

	/**
	* APP包管理列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("appmanage:app:list")
	@ApiOperation(value="返回APP包管理列表界面", notes="返回APP包管理列表界面")
	String App(){
	    return preUrl + "/app";
	}


	/**
	* APP包管理列表数据
	* @param params
	* @return
	*/
	@Log("APP包管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("appmanage:app:list")
	@ApiOperation(value="获取APP包管理列表接口", notes="获取APP包管理列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AppDO> appList = appService.list(query);
		int total = appService.count(query);
		PageUtils pageUtils = new PageUtils(appList, total);
		return pageUtils;
	}

	/**
	* APP包管理详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("appmanage:app:view")
	@ApiOperation(value="返回APP包管理详情页面", notes="返回APP包管理详情页面")
	String view(@PathVariable("id") String id,Model model){
			AppDO app = appService.get(id);
		model.addAttribute("app", app);
		return preUrl + "/view";
	}

	/**
	* APP包管理增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="返回APP包管理增加页面", notes="返回APP包管理增加页面")
	String add(Model model){
		model.addAttribute("appTypeCode",dictContentService.listDictByCode("appTypeCode"));
		model.addAttribute("appCombineCode",dictContentService.listDictByCode("appCombineCode"));
		return preUrl + "/add";
	}


	/**
	 * 保存APP包管理
	 * @param app
     * @return R
	 */
	@Log("APP包管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="保存APP包管理接口", notes="保存APP包管理接口")
	public R save( AppDO app){
		Date date = new Date();
		app.setCreateTime(date);
		app.setModifyTime(date);
		if(appService.save(app)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑APP包管理页面
    * @param appId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{appId}")
	@RequiresPermissions("appmanage:app:edit")
	@ApiOperation(value="返回APP包管理编辑页面", notes="返回APP包管理编辑页面")
	String edit(@PathVariable("appId") String appId,Model model){
		AppDO app = appService.get(appId);
		model.addAttribute("app", app);
		model.addAttribute("appTypeCode",dictContentService.listDictByCode("appTypeCode"));
		model.addAttribute("appCombineCode",dictContentService.listDictByCode("appCombineCode"));
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新APP包管理
	 * @param app
	 * @return R
	 */
	@Log("APP包管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("appmanage:app:edit")
	@ApiOperation(value="更新APP包管理接口", notes="更新APP包管理接口")
	public R update( AppDO app){
		Date date = new Date();
		app.setModifyTime(date);
		if(appService.update(app)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除APP包管理
	 * @param appId
	 * @return R
	 */
	@Log("APP包管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("appmanage:app:del")
	@ApiOperation(value="删除APP包管理接口", notes="删除APP包管理接口")
	public R remove( String appId){
		if(appService.del(appId)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除APP包管理
	 * @param appIds
	 * @return R
	 */
	@Log("APP包管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("appmanage:app:batchDel")
	@ApiOperation(value="批量删除APP包管理接口", notes="批量删除APP包管理接口")
	public R remove(@RequestParam("ids[]") String[] appIds){
		if(appService.batchDel(appIds)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}
}
