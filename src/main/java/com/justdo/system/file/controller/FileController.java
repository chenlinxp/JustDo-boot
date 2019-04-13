package com.justdo.system.file.controller;

import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.*;
import com.justdo.config.JustdoConfig;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author chenlin
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
	String fileDao(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "system/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:file:file")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<FileDO> fileDaoList = fileService.list(query);
		int total = fileService.count(query);
		PageUtils pageUtils = new PageUtils(fileDaoList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:file:add")
	String add() {
		return "system/file/add";
	}

	@GetMapping("/update")
	@RequiresPermissions("system:file:edit")
	String edit(String id, Model model) {
		FileDO file = fileService.get(id);
		model.addAttribute("file", file);
		return "system/file/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("system:file:info")
	public R info(@PathVariable("id") String id) {
		FileDO file = fileService.get(id);
		return R.ok().put("file", file);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:file:save")
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
	@RequiresPermissions("system:file:update")
	public R update(@RequestBody FileDO file) {
		fileService.update(file);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:file:del")
	public R remove(String id, HttpServletRequest request) {

		String fileName = justdoConfig.getUploadPath() + fileService.get(id).getFileUrl().replace("/files/", "");
		if (fileService.del(id) > 0) {
			boolean b = FileUtils.deleteFile(fileName);
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
	@RequiresPermissions("system:file:del")
	public R remove(@RequestParam("ids[]") String[] ids) {

		fileService.batchDel(ids);
		return R.ok();
	}

	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		//request.getSession().getServletContext().getRealPath("/")+ path;
		String fileName = file.getOriginalFilename();
		fileName = FileUtils.renameToUUID(fileName);
		String path = StringUtils.defaultIfEmpty(justdoConfig.getUploadPath(), "/upload/");
		String realPath = request.getSession().getServletContext().getRealPath("/")+ path;
		FileDO _file = new FileDO(FileType.fileType(fileName), "/files/"+ fileName, new Date());
		try {
			FileUtils.uploadFile(file.getBytes(), path, fileName);
		} catch (Exception e) {
			return R.error();
		}
		if (fileService.save(_file) > 0) {
			return R.ok().put("fileName",_file.getFileUrl());
		}
		return R.error();
	}


}
