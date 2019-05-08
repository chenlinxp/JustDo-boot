package com.justdo.system.resource.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.R;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.resource.domain.ResourceDO;
import com.justdo.system.resource.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 资源菜单管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
 
@Controller
@RequestMapping("/system/resource")
public class ResourceController {

	private String preUrl="system/resource"  ;
	@Autowired
	private ResourceService resourceService;



	/**
	* 列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:resource:list")
	String Resource(){
	    return preUrl + "/resource";
	}


	/**
	* 列表数据
	* @param params
	* @return
	*/
	@Log("资源菜单管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:resource:list")
	List<ResourceDO> list(@RequestParam Map<String, Object> params){
		List<ResourceDO> resourceDOs = resourceService.list(params);
		return resourceDOs;

	}

	/**
	* 详情页面
	* @param resourceId
	* @return 详情页面路径
	*/
	@GetMapping("/view/{resourceId}")
	@RequiresPermissions("system:resource:edit")
	String view(@PathVariable("resourceId") String resourceId,Model model){
			ResourceDO resource = resourceService.get(resourceId);
		model.addAttribute("resource", resource);
		return preUrl + "/view";
	}

	/**
	* 增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:resource:add")
	String add(){
		return preUrl + "/add";
	}


	/**
	 * 保存
	 * @param resource
     * @return R
	 */
	@Log("资源菜单管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:resource:add")
	public R save( ResourceDO resource){
		Date date = new Date();
		resource.setCreateTime(date);
		resource.setModifyTime(date);
		if(resourceService.save(resource)>0){
			return R.ok();
		}
		return R.error(1, "保存失败");
	}

	/**
	* 编辑页面
    * @param resourceId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{resourceId}")
	@RequiresPermissions("system:resource:edit")
	String edit(@PathVariable("resourceId") String resourceId,Model model){
		ResourceDO resource = resourceService.get(resourceId);
		String pId = resource.getParentId();
		if (StringUtils.isNotEmpty(pId) && !pId.equals("0")) {
			model.addAttribute("pName", resourceService.get(pId).getResourceName());
		} else {
			model.addAttribute("pName", "根目录");
		}
		model.addAttribute("resource", resource);
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新
	 * @param resource
	 * @return R
	 */
	@Log("资源菜单管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:resource:edit")
	public R update( ResourceDO resource){
		Date date = new Date();
		resource.setModifyTime(date);
		if (resourceService.update(resource) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败!");
		}
	}
	
	/**
	 * 删除
	 * @param resourceId
	 * @return R
	 */
	@Log("资源菜单管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:resource:del")
	public R remove( String resourceId){
		Map<String,Object > map = new HashMap<>();
		map.put("parentId",resourceId);
		if(resourceService.count(map)>0) {
			return R.error(1, "有下级资源不能删除");
		}else{
			if (resourceService.del(resourceId) > 0) {
				return R.ok();
			} else {
				return R.error(1, "删除失败");
			}
		}
	}
	
	/**
	 * 批量删除
	 * @param resourceIds
	 * @return R
	 */
	@Log("资源菜单管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:resource:batchDel")
	public R remove(@RequestParam("ids[]") String[] resourceIds){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", resourceIds[0]);
		if(resourceService.count(map)>0) {
			return R.error(1, "包含下级菜单,不允许删除");
		}
		if(resourceService.batchDel(resourceIds)>0){
			return R.ok();
		}else {
			return R.error(1, "删除失败");
		}
	}

	/**
	 * 资源树
	 * @return
	 */
	@GetMapping("/tree")
	@ResponseBody
	@ApiOperation(value="获取资源树接口", notes="获取资源树接口")
	Tree<ResourceDO> tree() {
		Tree<ResourceDO> tree = new Tree<ResourceDO>();
		tree = resourceService.getTree();
		return tree;
	}

	/**
	 * 资源树页面
	 * @return
	 */
	@GetMapping("/treeView")
	@ApiOperation(value="获取资源树界面", notes="获取资源树界面")
	String treeView() {
		return  preUrl + "/resourceTree";
	}

	/**
	 * 根据角色ID获取资源树
	 * @param roleId
	 * @return
	 */
	@GetMapping("/tree/{roleId}")
	@ResponseBody
	Tree<ResourceDO> tree(@PathVariable("roleId") String roleId) {
		Tree<ResourceDO> tree = new Tree<ResourceDO>();
		tree = resourceService.getTree(roleId);
		return tree;
	}
}
