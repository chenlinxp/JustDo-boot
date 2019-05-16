package com.justdo.system.position.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.position.domain.PositionDO;
import com.justdo.system.position.service.PositionService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 岗位管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-16 23:21:36
 */
 
@Controller
@RequestMapping("/system/position")
public class PositionController {

	private String preUrl="system/position"  ;
	@Autowired
	private PositionService positionService;

	@Autowired
	DictContentService dictContentService;

	/**
	* 岗位管理列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:position:list")
	@ApiOperation(value="返回岗位管理列表界面", notes="返回岗位管理列表界面")
	String Position(){
	    return preUrl + "/position";
	}


	/**
	* 岗位管理列表数据
	* @param params
	* @return
	*/
	@Log("岗位管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:position:list")
	@ApiOperation(value="获取岗位管理列表接口", notes="获取岗位管理列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PositionDO> positionList = positionService.list(query);
		int total = positionService.count(query);
		PageUtils pageUtils = new PageUtils(positionList, total);
		return pageUtils;
	}

	/**
	* 岗位管理详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:position:view")
	@ApiOperation(value="返回岗位管理详情页面", notes="返回岗位管理详情页面")
	String view(@PathVariable("id") String id,Model model){
			PositionDO position = positionService.get(id);
		model.addAttribute("position", position);
		return preUrl + "/view";
	}

	/**
	 * 岗位管理详情接口
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("system:position:view")
	@ApiOperation(value="返回岗位管理详情", notes="返回岗位管理详情")
	PositionDO info(@PathVariable("id") String id){
			PositionDO position = positionService.get(id);

		return position;
	}

	/**
	* 岗位管理增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:position:add")
	@ApiOperation(value="返回岗位管理增加页面", notes="返回岗位管理增加页面")
	String add(Model model){
		model.addAttribute("validCode",dictContentService.listDictByCode("validCode"));
		return preUrl + "/add";
	}


	/**
	 * 保存岗位管理
	 * @param position
     * @return R
	 */
	@Log("岗位管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:position:add")
	@ApiOperation(value="保存岗位管理接口", notes="保存岗位管理接口")
	public R save( PositionDO position){
		if(positionService.save(position)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑岗位管理页面
    * @param postid
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{postid}")
	@RequiresPermissions("system:position:edit")
	@ApiOperation(value="返回岗位管理编辑页面", notes="返回岗位管理编辑页面")
	String edit(@PathVariable("postid") String postid,Model model){
		PositionDO position = positionService.get(postid);
		model.addAttribute("position", position);
		model.addAttribute("validCode",dictContentService.listDictByCode("validCode"));
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新岗位管理
	 * @param position
	 * @return R
	 */
	@Log("岗位管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:position:edit")
	@ApiOperation(value="更新岗位管理接口", notes="更新岗位管理接口")
	public R update( PositionDO position){
		if(positionService.update(position)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除岗位管理
	 * @param postid
	 * @return R
	 */
	@Log("岗位管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:position:del")
	@ApiOperation(value="删除岗位管理接口", notes="删除岗位管理接口")
	public R remove( String postid){
		if(positionService.del(postid)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除岗位管理
	 * @param postids
	 * @return R
	 */
	@Log("岗位管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:position:batchDel")
	@ApiOperation(value="批量删除岗位管理接口", notes="批量删除岗位管理接口")
	public R remove(@RequestParam("ids[]") String[] postids){
		if(positionService.batchDel(postids)>0){
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}
}
