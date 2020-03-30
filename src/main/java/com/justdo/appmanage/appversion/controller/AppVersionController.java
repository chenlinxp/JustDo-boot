package com.justdo.appmanage.appversion.controller;

import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.appmanage.appversion.service.AppVersionService;
import com.justdo.common.annotation.Log;
import com.justdo.common.utils.R;
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
 * APP包版本记录管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-18 13:10:53
 */
 
@Controller
@RequestMapping("/appmanage/appversion")
public class AppVersionController {

	private String preUrl="appmanage/appversion"  ;
	@Autowired
	private AppVersionService appVersionService;

	@Autowired
	DictContentService dictContentService;

	/**
	* APP包版本记录管理列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("appmanage:appversion:list")
	@ApiOperation(value="返回APP包版本记录管理列表界面", notes="返回APP包版本记录管理列表界面")
	String AppVersion(){
	    return preUrl + "/appversion";
	}


	/**
	* APP包版本记录管理列表数据
	* @param params
	* @return
	*/
	@Log("APP包版本记录管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("appmanage:appversion:list")
	@ApiOperation(value="获取APP包版本记录管理列表接口", notes="获取APP包版本记录管理列表接口")
//	public PageUtils list(@RequestParam Map<String, Object> params){
//		//查询列表数据
//        Query query = new Query(params);
//		List<AppVersionDO> appVersionList = appVersionService.list(query);
//		int total = appVersionService.count(query);
//		PageUtils pageUtils = new PageUtils(appVersionList, total);
//		return pageUtils;
//	}
	public List<AppVersionDO> list(@RequestParam Map<String, Object> params){

		params.put("delFlag","0");
		return appVersionService.list(params);
	}
	/**
	* APP包版本记录管理详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("appmanage:appVersion:view")
	@ApiOperation(value="返回APP包版本记录管理详情页面", notes="返回APP包版本记录管理详情页面")
	String view(@PathVariable("id") String id,Model model){
			AppVersionDO appVersion = appVersionService.get(id);
		model.addAttribute("appVersion", appVersion);
		return preUrl + "/view";
	}

	/**
	 * APP包版本记录管理详情接口
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("appmanage:appVersion:view")
	@ApiOperation(value="返回APP包版本记录管理详情", notes="返回APP包版本记录管理详情")
	AppVersionDO info(@PathVariable("id") String id){
			AppVersionDO appVersion = appVersionService.get(id);
		return appVersion;
	}

	/**
	* APP包版本记录管理增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("appmanage:appversion:add")
	@ApiOperation(value="返回APP包版本记录管理增加页面", notes="返回APP包版本记录管理增加页面")
	String add(Model model){
		model.addAttribute("displayCode",dictContentService.listDictByCode("displayCode"));
		return preUrl + "/add";
	}


	/**
	 * 保存APP包版本记录管理
	 * @param appVersion
     * @return R
	 */
	@Log("APP包版本记录管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("appmanage:appversion:add")
	@ApiOperation(value="保存APP包版本记录管理接口", notes="保存APP包版本记录管理接口")
	public R save( AppVersionDO appVersion){
		Date date = new Date();
		appVersion.setCreateTime(date);
		if(appVersionService.save(appVersion)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑APP包版本记录管理页面
    * @param appVersionId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{appVersionId}")
	@RequiresPermissions("appmanage:appVersion:edit")
	@ApiOperation(value="返回APP包版本记录管理编辑页面", notes="返回APP包版本记录管理编辑页面")
	String edit(@PathVariable("appVersionId") String appVersionId,Model model){
		AppVersionDO appVersion = appVersionService.get(appVersionId);
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("displayCode",dictContentService.listDictByCode("displayCode"));
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新APP包版本记录管理
	 * @param appVersion
	 * @return R
	 */
	@Log("APP包版本记录管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("appmanage:appversion:edit")
	@ApiOperation(value="更新APP包版本记录管理接口", notes="更新APP包版本记录管理接口")
	public R update( AppVersionDO appVersion){
		if(appVersionService.update(appVersion)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除APP包版本记录管理
	 * @param appVersionId
	 * @return R
	 */
	@Log("APP包版本记录管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("appmanage:appversion:del")
	@ApiOperation(value="删除APP包版本记录管理接口", notes="删除APP包版本记录管理接口")
	public R remove( String appVersionId){
		if(appVersionService.del(appVersionId)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除APP包版本记录管理
	 * @param appVersionIds
	 * @return R
	 */
	@Log("APP包版本记录管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("appmanage:appversion:batchDel")
	@ApiOperation(value="批量删除APP包版本记录管理接口", notes="批量删除APP包版本记录管理接口")
	public R remove(@RequestParam("ids[]") String[] appVersionIds){
		if(appVersionService.batchDel(appVersionIds)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}

	/**
	 * APP包版本记录的隐藏
	 * @param appVersionId
	 * @return R
	 */
	@Log("APP包版本记录的隐藏")
	@PostMapping( "/hidden")
	@ResponseBody
	@RequiresPermissions("appmanage:appversion:hidden")
	@ApiOperation(value="隐藏APP包版本记录管理接口", notes="隐藏APP包版本记录管理接口")
	public R hidden( String appVersionId){
		AppVersionDO appVersion = appVersionService.get(appVersionId);
		if(appVersion!=null){
			int state = 1;
			if(appVersion.getDisplayState()==1){
				state = 0;
			}
			appVersion.setDisplayState(state);
			if(appVersionService.update(appVersion)>0){
				return R.ok();
			}
			return R.ok();
		}
		return R.error(1, "处理失败!");
	}

}
