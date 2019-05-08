package com.justdo.system.dict.controller;


import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.dict.domain.DictContentDO;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.dicttype.domain.DictTypeDO;
import com.justdo.system.dicttype.service.DictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




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
	@RequiresPermissions("system:dict:list")
	String dict(Model model) {

		model.addAttribute("title", "数据字典列表");
		model.addAttribute("keywords", "数据字典");
		model.addAttribute("description", "数据字典列表");

		return "system/dict/dict";
	}

	@Log("数据字典列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:dict:list")
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
	@Log("增加字典内容")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:dict:add")
	public R save(DictContentDO dictContent) {
			Map<String, Object> map = new HashMap<>(16);
			map.put("dccode",dictContent.getDccode());
			map.put("did",dictContent.getDid());
			List<DictContentDO> dictContentList = dictContentService.list(map);
	    if(dictContentList.size()==0){
			if (dictContentService.save(dictContent) > 0) {
				return R.ok();
			}else {
				return R.error("增加字典内容失败！");
			}
		}else {
			return R.error("此字典编码重复，请重新输入！");
		}
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:dict:edit")
	String edit(@PathVariable("id") String id, Model model) {
		DictContentDO dictContent = dictContentService.get(id);
		model.addAttribute("dictContent", dictContent);
		return "system/dict/edit";
	}

	@GetMapping("/view/{id}")
	@RequiresPermissions("system:dict:view")
	String view(@PathVariable("id") String id, Model model) {
		DictContentDO dictContent = dictContentService.get(id);
		model.addAttribute("dictContent", dictContent);
		return "system/dict/view";
	}

	/**
	 * 修改
	 */
	@Log("编辑字典内容")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:dict:edit")
	public R update(DictContentDO dictContent) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("dcid",dictContent.getDcid());
		map.put("dccode",dictContent.getDccode());
		map.put("did",dictContent.getDid());

		List<DictContentDO> dictContentList = dictContentService.list(map);
		if(dictContentList.size()>=1){
			return R.error("此字典编码重复，请重新输入！");
		}else {
			if (dictContentService.update(dictContent) > 0) {
				return R.ok();
			}else {
				return R.error("更新字典内容失败！");
			}
		}

	}

	/**
	 * 删除
	 */
	@Log("删除字典内容")
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:dict:del")
	public R del(String id) {
		if (dictContentService.del(id) > 0) {
			return R.ok();
		}
		return R.error("删除字典内容失败！");
	}

	/**
	 * 删除
	 */
	@Log("批量删除字典内容")
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:dict:batchDel")
	public R del(@RequestParam("ids[]") String[] ids) {
		if (dictContentService.batchDel(ids)> 0) {
			return R.ok();
		}
		return R.ok("批量删除字典内容失败！");
	}


	/**
	 * 根据字典索引编码获取字典列
	 * @param dictCode
	 * @return
	 */
	@ResponseBody
	@GetMapping("/list/{dictCode}")
	public List<DictContentDO> listByType(@PathVariable("dictCode") String dictCode) {
		List<DictContentDO> dictList = dictContentService.listDictByCode(dictCode);
		return dictList;
	}

	/*------------------------------------*/
	/**
	 *数据字典索引的列表
	 */
	@GetMapping("/dicttype")
	@RequiresPermissions("system:dict:list")
	@ResponseBody
	public List<DictTypeDO> listDictType(@RequestParam Map<String, Object> params) {
		return dictTypeService.list(params);
	};

	@GetMapping("/addtype")
	@RequiresPermissions("system:dict:add")
	String adddict() {
		return "system/dict/addtype";
	}

	/**
	 * 保存数据字典索引
	 */
	@Log("增加字典索引")
	@ResponseBody
	@PostMapping("/savetype")
	@RequiresPermissions("system:dict:add")
	public R savetype(DictTypeDO dicttype) {

		Map<String, Object> map = new HashMap<>(16);
		map.put("dcode",dicttype.getDcode());
		List<DictTypeDO> dictTypeDOList = dictTypeService.list(map);
		if(dictTypeDOList.size()==0){
		if (dictTypeService.save(dicttype) > 0) {
			return R.ok();
			}else{
				return R.error("增加字典索引失败！");
			}
		}
		else{
			return R.error("此字典数据值已存在，请重新输入！");
		}
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
	@Log("编辑字典索引")
	@ResponseBody
	@PostMapping("/updatetype")
	@RequiresPermissions("system:dict:edit")
	public R edittype(DictTypeDO dicttype) {

		Map<String, Object> map = new HashMap<>(16);
		map.put("did",dicttype.getDid());
		map.put("dcode",dicttype.getDcode());
		List<DictTypeDO> dictTypeDOList = dictTypeService.list(map);
		if(dictTypeDOList.size()>1){
			return R.error("此字典数据值已存在，请重新输入！");
		}
		else{
			if (dictTypeService.update(dicttype) > 0) {
				return R.ok();
			}else{
				return R.error("更新字典索引失败！");
			}
		}
	}
	/**
	 * 删除数据索引
	 */
	@Log("删除字典索引")
	@PostMapping("/deltype")
	@ResponseBody
	@RequiresPermissions("system:dict:batchDel")
	public R deltype(@RequestParam("id") String id) {
		if(dictContentService.listByTypeId(id).size()==0) {
			if (dictTypeService.del(id) > 0) {
				return R.ok();
			}else{
				return R.error("删除字典索引失败！");
			}
		}else{
			return R.error("此类型下有数据不能删除");
		}
	}

}
