package com.justdo.system.dict.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.dict.domain.DictContentDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.justdo.common.controller.BaseController;
import com.justdo.common.config.Constant;
import com.justdo.system.dicttype.domain.DictTypeDO;
import com.justdo.system.dicttype.service.DictTypeService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;




/**
 * 字典表
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */

@Controller
@RequestMapping("/system/dict")
public class DictController extends BaseController {
	@Autowired
	private DictContentService dictContentService;

	@Autowired
	private DictTypeService dictTypeService;

	@GetMapping()
	@RequiresPermissions("system:dict:dict")
	String dict(Model model) {

		model.addAttribute("title", "数据字典列表");
		model.addAttribute("keywords", "数据字典");
		model.addAttribute("description", "数据字典列表");

		return "system/dict/dict";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:dict:dict")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<DictContentDO> dictContentList = dictContentService.list(query);
		int total = dictContentService.count(query);
		PageUtils pageUtils = new PageUtils(dictContentList, total);
		return pageUtils;
	}

	@GetMapping("/add/{id}")
	@RequiresPermissions("system:dict:add")
	String add(@PathVariable("id") String id, Model model) {
		model.addAttribute("did", id);
		return "system/dict/add";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:dict:add")
	public R save(DictContentDO dictContent) {
		if (dictContentService.save(dictContent) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:dict:edit")
	String edit(@PathVariable("id") String id, Model model) {
		DictContentDO dictContent = dictContentService.get(id);
		model.addAttribute("dictContent", dictContent);
		return "system/dict/edit";
	}

	@GetMapping("/view/{id}")
	@RequiresPermissions("system:dict:dict")
	String view(@PathVariable("id") String id, Model model) {
		DictContentDO dictContent = dictContentService.get(id);
		model.addAttribute("dictContent", dictContent);
		return "system/dict/view";
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("system:dict:edit")
	public R update(DictContentDO dictContent) {
		dictContentService.update(dictContent);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:dict:del")
	public R del(String id) {
		if (dictContentService.del(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:dict:batchDel")
	public R del(@RequestParam("ids[]") String[] ids) {
		dictContentService.batchDel(ids);
		return R.ok();
	}

	// 类别已经指定增加
	@GetMapping("/add/{type}/{description}")
	@RequiresPermissions("system:dict:add")
	String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {

		model.addAttribute("type", type);
		model.addAttribute("description", description);
		return "system/dict/add";
	}

	@ResponseBody
	@GetMapping("/list/{type}")
	public List<DictContentDO> listByType(@PathVariable("type") String type) {
		// 查询列表数据
		Map<String, Object> map = new HashMap<>(16);
		map.put("type", type);
		List<DictContentDO> dictList = dictContentService.list(map);
		return dictList;
	}

	/*------------------------------------*/
	/**
	 *数据字典索引的列表
	 */
	@GetMapping("/dicttype")
	@RequiresPermissions("system:dict:dict")
	@ResponseBody
	public List<DictTypeDO> listDictType(@RequestParam("dname") String dname) {

		return dictTypeService.list(dname);
	};

	@GetMapping("/addtype")
	@RequiresPermissions("system:dict:add")
	String adddict() {
		return "system/dict/addtype";
	}

	/**
	 * 保存数据字典索引
	 */
	@ResponseBody
	@PostMapping("/savetype")
	@RequiresPermissions("system:dict:add")
	public R savetype(DictTypeDO dicttype) {
		if (dictTypeService.save(dicttype) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@GetMapping("/edittype/{id}")
	@RequiresPermissions("system:dict:edit")
	String editdict(@PathVariable("id") String id, Model model) {
		DictTypeDO dictTypeDO = dictTypeService.get(id);
		model.addAttribute("dictTypeDO", dictTypeDO);
		return "system/dict/edittype";
	}

	/**
	 * 更新数据字典索引
	 */
	@ResponseBody
	@PostMapping("/updatetype")
	@RequiresPermissions("system:dict:edit")
	public R edittype(DictTypeDO dicttype) {
		if (dictTypeService.update(dicttype) > 0) {
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 删除
	 */
	@PostMapping("/deltype")
	@ResponseBody
	@RequiresPermissions("system:dict:batchDel")
	public R deltype(@RequestParam("id") String id) {
		if(dictContentService.listByType(id).size()==0) {
			dictTypeService.del(id);
			return R.ok();
		}else{
			return R.error("此类型下有数据不能删除");
		}
	}



}
