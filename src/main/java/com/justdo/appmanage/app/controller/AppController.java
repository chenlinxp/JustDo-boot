package com.justdo.appmanage.app.controller;

import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import com.justdo.appmanage.appversion.dao.AppVersionDao;
import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.common.annotation.Log;
import com.justdo.common.domain.APPInfoBean;
import com.justdo.common.utils.*;
import com.justdo.config.JustdoConfig;
import com.justdo.system.dict.service.DictContentService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * APP包管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-15 20:17:22
 */
 
@Controller
@RequestMapping("/appmanage/app")
public class AppController {

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	private String preUrl="appmanage/app"  ;
	@Autowired
	private AppService appService;

	@Autowired
	private AppVersionDao appVersionDao;

	@Autowired
	DictContentService dictContentService;

	@Autowired
	private JustdoConfig justdoConfig;



//	@InitBinder
//	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//	}

	/**
	* APP包管理列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("appmanage:app:list")
	@ApiOperation(value="返回APP包管理列表界面", notes="返回APP包管理列表界面")
	String App(){
	    return preUrl + "/app";
	}


	/**
	* APP包管理列表数据
	* @param params
	* @return
	*/
	@Log("APP包管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("appmanage:app:list")
	@ApiOperation(value="获取APP包管理列表接口", notes="获取APP包管理列表接口")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AppDO> appList = appService.list(query);
		int total = appService.count(query);
		PageUtils pageUtils = new PageUtils(appList, total);
		return pageUtils;
	}

	/**
	* APP包管理详情页面
	* @param id
	* @return 详情页面路径
	*/
	@GetMapping("/view/{id}")
	@RequiresPermissions("appmanage:app:view")
	@ApiOperation(value="返回APP包管理详情页面", notes="返回APP包管理详情页面")
	String view(@PathVariable("id") String id,Model model){
			AppDO app = appService.get(id);
		model.addAttribute("app", app);
		return preUrl + "/view";
	}

	/**
	* APP包管理增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="返回APP包管理增加页面", notes="返回APP包管理增加页面")
	String add(Model model){
		model.addAttribute("appTypeCode",dictContentService.listDictByCode("appTypeCode"));
		model.addAttribute("appCombineCode",dictContentService.listDictByCode("appCombineCode"));
		return preUrl + "/add";
	}

	/**
	 * APP包管理增加页面
	 * @return 增加页面路径
	 */
	@GetMapping("/upload")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="返回APP包管理上传页面", notes="返回APP包管理上传页面")
	String upload(Model model){
		model.addAttribute("appTypeCode",dictContentService.listDictByCode("appTypeCode"));
		model.addAttribute("appCombineCode",dictContentService.listDictByCode("appCombineCode"));
		return preUrl + "/upload";
	}

	/**
	 * 上传APP安装包
	 * @return R
	 */
	@Log("上传APP安装包")
	@ResponseBody
	@PostMapping(value = "/upload")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="上传APP安装包", notes="上传APP安装包")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request , HttpServletResponse response) throws FileNotFoundException {

		response.setContentType("text/html; charset=UTF-8");

		response.setHeader("Content-Type","text/html; charset=utf-8");   //);

		String fileName = file.getOriginalFilename();

		String extention = FileUtils.getFileTypeByName(fileName);

		fileName = FileUtils.renameToUUID(fileName);

		File filePath=new File(ResourceUtils.getURL("classpath:").getPath());

		System.out.println(filePath.getAbsolutePath());

		File upload=new File(justdoConfig.getAppUploadPath());

		String pythonShellPath = justdoConfig.getPythonShellPath();

		String dateStr = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

		APPInfoBean aPPInfoBean = null;

//		CommonsMultipartFile cFile = (CommonsMultipartFile)file;
//		DiskFileItem diskFileItem = (DiskFileItem)cFile.getFileItem();
//		File zipfile = diskFileItem.getStoreLocation();
		File uploadAppPath = null;
		if(!upload.exists()){
			upload.mkdirs();
			System.out.println(upload.getAbsolutePath());
			System.out.println(upload.getPath());
			logger.info("-------------1");
			logger.info(upload.getAbsolutePath());
			logger.info("-------------2");
			logger.info(upload.getPath());
		}
		logger.info("-------------3");
		logger.info(upload.getPath());
		//String realPath = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/classes/"+ path;
		//String realPath = request.getSession().getServletContext().getRealPath("/")+ path;
		//FileDO _file = new FileDO(FileType.fileType(fileName), "/files/"+ fileName, new Date());
		AppDO appDo = new AppDO();
		try {
			File dest = null;
			File tmp = null;
			String appPath = null;
			String iconPath = null;
			if(extention.equalsIgnoreCase(".apk")){

				 iconPath = upload.getPath()+"/apk/";
				 tmp = new File(iconPath+fileName);
				 file.transferTo(tmp);

				 aPPInfoBean = APPUtils.readAPK(tmp,iconPath,justdoConfig.getAaptPath());
				 appPath = upload.getPath()+"/apk/"+aPPInfoBean.getAppName()+"/"+aPPInfoBean.getVersionName()+"/"+dateStr;

				 aPPInfoBean.setAppPath(appPath);
				 aPPInfoBean.setAppRename(fileName);
				 uploadAppPath = new File(appPath);
				 if(!uploadAppPath.exists()){
					 uploadAppPath.mkdirs();
				 }
				 dest = new File(appPath+"/"+fileName);
				 file.transferTo(dest);
				 tmp.delete();

			}else{

				 iconPath = upload.getPath()+"/ipa/";
				 tmp = new File(iconPath+fileName);
				 file.transferTo(tmp);
				 aPPInfoBean =  APPUtils.readIPA(tmp,iconPath,pythonShellPath);
				 appPath = upload.getPath()+"/ipa/"+aPPInfoBean.getAppName()+"/"+aPPInfoBean.getVersionName()+"/"+dateStr;

				aPPInfoBean.setAppPath(appPath);
				aPPInfoBean.setAppRename(fileName);
				 uploadAppPath = new File(appPath);
				 if(!uploadAppPath.exists()){
					uploadAppPath.mkdirs();
				 }
				 dest = new File(appPath+"/"+fileName);
				 file.transferTo(dest);
				 APPUtils.createPlist(dest.getPath(), aPPInfoBean,justdoConfig.getBaseAddress());
				 tmp.delete();

			}

		} catch (Exception e) {
			return R.error(1, e.getMessage());
		}
		if (appService.save(aPPInfoBean) > 0) {
			return R.ok();
		}
		return R.error(1, "保存失败!");
	}


	/**
	 * 保存APP包管理
	 * @param app
     * @return R
	 */
	@Log("APP包管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("appmanage:app:add")
	@ApiOperation(value="保存APP包管理接口", notes="保存APP包管理接口")
	public R save( AppDO app){
		Date date = new Date();
		app.setCreateTime(date);
		app.setModifyTime(date);
		if(appService.save(app)>0){
			return R.ok();
		}
		return R.error(1, "保存失败!");

	}

	/**
	* 编辑APP包管理页面
    * @param appId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{appId}")
	@RequiresPermissions("appmanage:app:edit")
	@ApiOperation(value="返回APP包管理编辑页面", notes="返回APP包管理编辑页面")
	String edit(@PathVariable("appId") String appId,Model model){
		AppDO app = appService.get(appId);
		model.addAttribute("app", app);
		model.addAttribute("appTypeCode",dictContentService.listDictByCode("appTypeCode"));
		model.addAttribute("appCombineCode",dictContentService.listDictByCode("appCombineCode"));
        int appType = app.getAppType();
        String appPackageName = app.getBundleId();
		Map<String, Object> map = new HashedMap();
		map.put("bundleId",appPackageName);
        if(appType == 1){
	        map.put("appType","2");
        }else{
	        map.put("appType","1");
        }
		List<AppDO> appLists = appService.list(map);

		model.addAttribute("appLists", appLists);
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新APP包管理
	 * @param app
	 * @return R
	 */
	@Log("APP包管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("appmanage:app:edit")
	@ApiOperation(value="更新APP包管理接口", notes="更新APP包管理接口")
	public R update( AppDO app){
		Date date = new Date();
		app.setModifyTime(date);
		if(appService.update(app)>0){
			return R.ok();
		}
		return R.error(1, "更新失败!");
	}
	
	/**
	 * 删除APP包管理
	 * @param appId
	 * @return R
	 */
	@Log("APP包管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("appmanage:app:del")
	@ApiOperation(value="删除APP包管理接口", notes="删除APP包管理接口")
	public R remove( String appId){
		if(appService.del(appId)>0){
		return R.ok();
		}
		return R.error(1, "删除失败!");
	}
	
	/**
	 * 批量删除APP包管理
	 * @param appIds
	 * @return R
	 */
	@Log("APP包管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("appmanage:app:batchDel")
	@ApiOperation(value="批量删除APP包管理接口", notes="批量删除APP包管理接口")
	public R remove(@RequestParam("ids[]") String[] appIds){

		List<String> pathNames = new ArrayList<>();

		for(int i = 0;i < appIds.length;i++){

			AppDO app = appService.get(appIds[i]);
			if(app!=null){
				String folderName = app.getFolderName();
				String pathName = justdoConfig.getAppUploadPath();
				if(app.getAppType()==1){
					pathName =  pathName+"apk/"+folderName;
				}else{
					pathName =  pathName+"ipa/"+folderName;
				}
				pathNames.add(pathName);
			}
		}

		if(appService.batchDel(appIds)>0){

			Iterator<String> itr = pathNames.iterator();
			while (itr.hasNext()){
				String pathName = itr.next();
				File file = new File(pathName);
				if(file.exists()&&file.isDirectory()){

					try {
						FileUtils.deleteFolder(file);
					} catch (Exception e) {
						e.printStackTrace();
						return R.error(1, "批量删除异常!");
					}
				}
			}
			return R.ok();
		}
		return R.error(1, "批量删除失败!");
	}

	/**
	 * APP包管理详情页面
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/download/{id}")
	@ApiOperation(value="返回APP下载页面", notes="返回APP下载页面")
	String downloadpage(@PathVariable("id") String id,Model model){
		AppDO app = appService.get(id);
		model.addAttribute("app", app);
		return preUrl + "/download";
	}

	/**
	 * APP包安装API
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/showAPI/{id}")
	@ApiOperation(value="返回APP下载页面", notes="返回APP下载页面")
	String showAPI(@PathVariable("id") String id,Model model){

		String APInfo = "暂无API地址!";
		String appName = "";
		String appBundleId = "";
		AppDO app = appService.get(id);
		AppVersionDO appVersionDO = null;
		List<AppVersionDO> appVersionDOList = null;
		if(app!=null){
			appName = app.getAppName();
			appBundleId = app.getBundleId();
			Map<String, Object> map = new HashMap<>(16);

			map.put("appId", app.getAppId());
			map.put("delFlag", "0");
			map.put("sort", "CREATE_TIME");
			map.put("order", "desc");
			appVersionDOList = appVersionDao.list(map);
			if(appVersionDOList.size()>0){
				APInfo =  appVersionDOList.get(0).getDownloadUrl();
			}
			if(app.getAppType()==2){
				APInfo = "itms-services://?action=download-manifest&url="+justdoConfig.getBaseAddress()+APInfo;
			}else{
				APInfo = justdoConfig.getBaseAddress()+APInfo;
			}
		}

		model.addAttribute("appName", appName);

		model.addAttribute("appBundleId", appBundleId);

		model.addAttribute("APInfo", APInfo);

		return preUrl + "/showAPI";
	}
}
