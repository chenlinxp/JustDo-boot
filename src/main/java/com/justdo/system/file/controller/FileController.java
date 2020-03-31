package com.justdo.system.file.controller;

import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.*;
import com.justdo.config.JustdoConfig;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
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

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private FileService fileService;

	@Autowired
	private JustdoConfig justdoConfig;

	@GetMapping()
	@RequiresPermissions("system:file:list")
	String fileDao(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "system/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:file:list")
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
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:file:view")
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
	@PostMapping("/update")
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
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws FileNotFoundException {

		String fileName = file.getOriginalFilename();
		fileName = FileUtils.renameToUUID(fileName);

		//String path = StringUtils.defaultIfEmpty(justdoConfig.getUploadPath(), "/upload/");

		//file.transferTo();

		File filePath=new File(ResourceUtils.getURL("classpath:").getPath());
		System.out.println(filePath.getAbsolutePath());
		//File upload=new File(filePath.getAbsolutePath(),justdoConfig.getUploadPath());

		File upload=new File(justdoConfig.getUploadPath());
		if(!upload.exists()){
			upload.mkdirs();
			System.out.println(upload.getAbsolutePath());
			System.out.println(upload.getPath());
			logger.info("-------------1");
			logger.info(upload.getAbsolutePath());
			logger.info("-------------2");
			logger.info(upload.getPath());

			//在开发测试模式时，得到地址为：{项目跟目录}/target/static/images/upload/
			//在打成jar正式发布时，得到的地址为:{发布jar包目录}/static/images/upload/
		}
		logger.info("-------------3");
		logger.info(upload.getPath());
		//String realPath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/classes/"+ path;
		//String realPath = request.getSession().getServletContext().getRealPath("/")+ path;
		FileDO _file = new FileDO(FileType.fileType(fileName), "/files/"+ fileName, new Date());
		try {
			File dest = new File(upload.getPath()+"/"+fileName);
			file.transferTo(dest);

		} catch (Exception e) {
			return R.error();
		}
		if (fileService.save(_file) > 0) {
			return R.ok().put("fileName",_file.getFileUrl());
		}
		return R.error();
	}


}
