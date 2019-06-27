package com.justdo.system.permissioninit.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.permissioninit.domain.PermissionInitDO;
import com.justdo.system.permissioninit.service.PermissionInitService;
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
 * shiro初始权限
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-06-27 12:44:13
 */
 
@Controller
@RequestMapping("/system/permissioninit")
public class PermissionInitController {

	private String preUrl="system/permissioninit"  ;
	@Autowired
	private PermissionInitService permissionInitService;

	@Autowired
	DictContentService dictContentService;

	/**
	* shiro初始权限列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:permissioninit:list")
	@ApiOperation(value="返回shiro初始权限列表界面", notes="返回shiro初始权限列表界面")
	String PermissionInit(){
	    return preUrl + "/permissioninit";
	}


	/**
	* shiro初始权限列表数据
	* @param params
	* @return
	*/
	@Log("shiro初始权限列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:permissioninit:list")
	@ApiOperation(value="获取shiro初始权限列表接口", notes="获取shiro初始权限列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PermissionInitDO> permissionInitList = permissionInitService.list(query);
		int total = permissionInitService.count(query);
		PageUtils pageUtils = new PageUtils(permissionInitList, total);
		return pageUtils;
	}

	/**
	* shiro初始权限详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:permissionInit:view")
	@ApiOperation(value="返回shiro初始权限详情页面", notes="返回shiro初始权限详情页面")
	String view(@PathVariable("id") String id,Model model){
			PermissionInitDO permissionInit = permissionInitService.get(id);
		model.addAttribute("permissionInit", permissionInit);
		return preUrl + "/view";
	}

	/**
	 * shiro初始权限详情接口
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("system:permissionInit:view")
	@ApiOperation(value="返回shiro初始权限详情", notes="返回shiro初始权限详情")
	PermissionInitDO info(@PathVariable("id") String id){
			PermissionInitDO permissionInit = permissionInitService.get(id);
		return permissionInit;
	}

	/**
	* shiro初始权限增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:permissioninit:add")
	@ApiOperation(value="返回shiro初始权限增加页面", notes="返回shiro初始权限增加页面")
	String add(Model model){
		return preUrl + "/add";
	}


	/**
	 * 保存shiro初始权限
	 * @param permissionInit
     * @return R
	 */
	@Log("shiro初始权限保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:permissioninit:add")
	@ApiOperation(value="保存shiro初始权限接口", notes="保存shiro初始权限接口")
	public R save( PermissionInitDO permissionInit){
		Date date = new Date();
		permissionInit.setCreateTime(date);
		permissionInit.setModifyTime(date);
		if(permissionInitService.save(permissionInit)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑shiro初始权限页面
    * @param permissionId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{permissionId}")
	@RequiresPermissions("system:permissionInit:edit")
	@ApiOperation(value="返回shiro初始权限编辑页面", notes="返回shiro初始权限编辑页面")
	String edit(@PathVariable("permissionId") String permissionId,Model model){
		PermissionInitDO permissionInit = permissionInitService.get(permissionId);
		model.addAttribute("permissionInit", permissionInit);
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新shiro初始权限
	 * @param permissionInit
	 * @return R
	 */
	@Log("shiro初始权限更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:permissioninit:edit")
	@ApiOperation(value="更新shiro初始权限接口", notes="更新shiro初始权限接口")
	public R update( PermissionInitDO permissionInit){
		Date date = new Date();
		permissionInit.setModifyTime(date);
		if(permissionInitService.update(permissionInit)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除shiro初始权限
	 * @param permissionId
	 * @return R
	 */
	@Log("shiro初始权限删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:permissioninit:del")
	@ApiOperation(value="删除shiro初始权限接口", notes="删除shiro初始权限接口")
	public R remove( String permissionId){
		if(permissionInitService.del(permissionId)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除shiro初始权限
	 * @param permissionIds
	 * @return R
	 */
	@Log("shiro初始权限批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:permissioninit:batchDel")
	@ApiOperation(value="批量删除shiro初始权限接口", notes="批量删除shiro初始权限接口")
	public R remove(@RequestParam("ids[]") String[] permissionIds){
		if(permissionInitService.batchDel(permissionIds)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}
}
