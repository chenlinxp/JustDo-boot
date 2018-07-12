package com.justdo.system.file.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.justdo.common.controller.BaseController;
import com.justdo.common.config.JustdoConfig;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import com.justdo.common.utils.*;

/**
 * 文件上传
 * 
 * @author chglee
 * @email
 * @date 2017-09-19 16:02:20
 */
@Controller
@RequestMapping("/system/file")
public class FileController extends BaseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private JustdoConfig justdoConfig;

	@GetMapping()
	@RequiresPermissions("system:file:file")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "system/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:file:file")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> sysFileList = fileService.list(query);
		int total = fileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:file:add")
	String add() {
		return "system/file/add";
	}

	@GetMapping("/edit")
	@RequiresPermissions("system:file:edit")
	String edit(Long id, Model model) {
		FileDO file = fileService.get(id);
		model.addAttribute("file", file);
		return "system/file/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("system:info")
	public R info(@PathVariable("id") Long id) {
		FileDO file = fileService.get(id);
		return R.ok().put("file", file);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:save")
	public R save(FileDO file) {
		if (fileService.save(file) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("system:update")
	public R update(@RequestBody FileDO file) {
		fileService.update(file);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ResponseBody
	// @RequiresPermissions("common:del")
	public R remove(Long id, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = justdoConfig.getUploadPath() + fileService.get(id).getUrl().replace("/files/", "");
		if (fileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:del")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		fileService.batchDel(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		//request.getSession().getServletContext().getRealPath("/")+ path;
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		String path = StringUtils.defaultIfEmpty(justdoConfig.getUploadPath(), "/upload/");
		//String realPath = request.getSession().getServletContext().getRealPath("/")+ path;
		FileDO _file = new FileDO(FileType.fileType(fileName), path+ fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), path, fileName);
		} catch (Exception e) {
			return R.error();
		}

		if (fileService.save(_file) > 0) {
			return R.ok().put("fileName",_file.getUrl());
		}
		return R.error();
	}


}
