package com.justdo.system.desksettings.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.desksettings.domain.DeskSettingsDO;
import com.justdo.system.desksettings.service.DeskSettingsService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.justdo.common.utils.ShiroUtils.getEmployeeId;

/**
 * 桌面设置
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-11-06 12:17:25
 */
 
@Controller
@RequestMapping("/system/desksettings")

public class DeskSettingsController {

	private String preUrl="system/desksettings"  ;
	@Autowired
	private DeskSettingsService deskSettingsService;


	/**
	* 桌面设置列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:desksettings:list")
	@ApiOperation(value="返回桌面设置列表界面", notes="返回桌面设置列表界面")
	String DeskSettings(){
	    return preUrl + "/desksettings";
	}


	/**
	* 桌面设置列表数据
	* @param params
	* @return
	*/
	@Log("桌面设置列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:desksettings:list")
	@ApiOperation(value="获取桌面设置列表接口", notes="获取桌面设置列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<DeskSettingsDO> deskSettingsList = deskSettingsService.list(query);
		int total = deskSettingsService.count(query);
		PageUtils pageUtils = new PageUtils(deskSettingsList, total);
		return pageUtils;
	}

	/**
	 * 桌面设置详情接口
	 * @return 详情页面路径
	 */
	@GetMapping("/info")
	@ResponseBody
	//@RequiresPermissions("system:deskSettings:view")
	//@ApiOperation(value="返回桌面设置详情", notes="返回桌面设置详情")
	public R  info(){

		String employeeId = getEmployeeId();

		DeskSettingsDO deskSettings = deskSettingsService.getByEmployeeId(employeeId);

		return R.ok("data",deskSettings);
	}


	/**
	 * 保存桌面设置
	 * @param deskSettings
     * @return R
	 */
	@Log("桌面设置保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:desksettings:add")
	@ApiOperation(value="保存桌面设置接口", notes="保存桌面设置接口")
	public R save( DeskSettingsDO deskSettings){
		if(deskSettingsService.save(deskSettings)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	

	/**
	 * 更新桌面设置
	 * @param deskSettings
	 * @return R
	 */
	@Log("桌面设置更新")
	@ResponseBody
	@PostMapping("/update")
	//@RequiresPermissions("system:desksettings:edit")
	@ApiOperation(value="更新桌面设置接口", notes="更新桌面设置接口")
	public R update( DeskSettingsDO deskSettings){

		String employeeId = getEmployeeId();

		DeskSettingsDO deskSettings2 = deskSettingsService.getByEmployeeId(employeeId);

		if(deskSettings2!=null){

			deskSettings.setSettingsId(deskSettings2.getSettingsId());

			deskSettings.setEmployeeId(employeeId);

			if(deskSettingsService.update(deskSettings)>0){
				return R.ok();
			}
		}
		else{

			deskSettings.setSettingsId(StringUtils.getUUID());

			deskSettings.setEmployeeId(employeeId);

			if(deskSettingsService.save(deskSettings)>0){
				return R.ok();
			}
		}
		return R.error(1, "更新失败!");
	}
}
