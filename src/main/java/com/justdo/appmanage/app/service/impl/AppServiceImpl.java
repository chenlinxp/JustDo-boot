package com.justdo.appmanage.app.service.impl;

import com.justdo.appmanage.app.dao.AppDao;
import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import com.justdo.appmanage.appversion.dao.AppVersionDao;
import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.common.domain.APPInfoBean;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.QRCodeUtils;
import com.justdo.common.utils.StringUtils;
import com.justdo.config.JustdoConfig;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AppServiceImpl implements AppService {

	@Autowired
	private AppDao appDao;

	@Autowired
	private AppVersionDao appVersionDao;

	@Autowired
	JustdoConfig justdoConfig;
	
	@Override
	public AppDO get(String appId){
		return appDao.get(appId);
	}


	@Override
	public AppDO getByBundleId(Map<String, Object> map){
		return appDao.getByBundleId(map);
	}
	
	@Override
	public List<AppDO> list(Map<String, Object> map){
		return appDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return appDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(AppDO app){
		return appDao.save(app);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(AppDO app){
		String combineAppId = app.getCombineAppId();
		int isCombine = app.getIsCombine();
		Date date = new Date();
		if(isCombine!=1&&StringUtils.isNotEmpty(combineAppId)){

			app.setCombineTime(date);
			app.setIsCombine(1);
			app.setCombineAppId(combineAppId);

			AppDO app2 = appDao.get(combineAppId);
			if(app2!=null){
				app2.setCombineAppId(app.getAppId());
				app2.setCombineTime(date);
				app2.setIsCombine(1);
				appDao.update(app2);
			}
		}
		if(isCombine==1&&!StringUtils.isNotEmpty(combineAppId)){
			app.setIsCombine(0);
			app.setCombineAppId("");

			AppDO app2 = appDao.getByCombineId(app.getAppId());
			if(app2!=null){
				app2.setCombineAppId("");
				app2.setIsCombine(0);
				appDao.update(app2);
			}
		}
		return appDao.update(app);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String appId){
		return appDao.del(appId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] appIds){
		return appDao.batchDel(appIds);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int  save(APPInfoBean app){
		String appName = app.getBundleName();
		String pageName = app.getPackageName();
		String versionName = app.getVersionName();
		String versionCode = app.getVersionCode();
		String logoImage = app.getLogoImage();
		//网站系统logo 访问路径
		String webLogoUrl = "/"+logoImage.substring(logoImage.lastIndexOf("app"), logoImage.length()).replace("app","files");
		String bundleName = app.getBundleName();
		String fileSize = app.getFileSize();
		String appId = StringUtils.getUUID();
//		a = a.substring(a.lastIndexOf("app"), a.length()).replace("app","files");
//		b = b.substring(b.lastIndexOf("app"), b.length()).replace("app","files");
//		c = c.substring(c.lastIndexOf("app"), c.length()).replace("app","files");
		String loadUrl = "/portal/app/"+appId;
		Date date = new Date();
		Map<String, Object> map = new HashedMap();
		map.put("bundleId",app.getPackageName());
		map.put("appType",app.getAppType());
		AppDO appDO = this.getByBundleId(map);
		if(appDO == null) {

			String appKey = StringUtils.getUUID();
			String qRCodeUrl = logoImage.substring(0, logoImage.lastIndexOf("/"))+"/";
			String shortUrl = StringUtils.shortUrl(loadUrl);
			String allShortURL = justdoConfig.getBaseAddress() + shortUrl;

			String a =	QRCodeUtils.encodeZxingCode(allShortURL,qRCodeUrl,250,app.getLogoImage());
			a = "/"+a.substring(a.lastIndexOf("app"), a.length()).replace("app","files");
			String b =  QRCodeUtils.encodeZxingCode(allShortURL,qRCodeUrl,500,app.getLogoImage());
			b = "/"+b.substring(b.lastIndexOf("app"), b.length()).replace("app","files");
			String c =  QRCodeUtils.encodeZxingCode(allShortURL,qRCodeUrl,800,app.getLogoImage());
			c = "/"+c.substring(c.lastIndexOf("app"), c.length()).replace("app","files");
			appDO = new AppDO(appId,appKey, appName, bundleName, pageName, shortUrl, loadUrl, DateUtils.addMonth(date,1), app.getAppType(), 0, "", null, webLogoUrl, a, b, c, "", date, date);

			appDao.save(appDO);

		}else{
			appId = appDO.getAppId();
			appDO.setIconUrl(webLogoUrl);
			appDO.setExpirerTime(date);
			appDO.setAppName(appName);
			appDO.setBundleName(bundleName);
			appDO.setModifyTime(date);
			appDao.update(appDO);
		}

        String  appVersionId = StringUtils.getUUID();
		String  versionLoadUrl = loadUrl+"?id="+appVersionId;
		String  versionLoadqRCode =justdoConfig.getBaseAddress()+versionLoadUrl;
		String  appPath =  app.getAppPath();

		String  d = QRCodeUtils.encodeZxingCode(versionLoadqRCode,appPath,250,app.getLogoImage());
		d = "/"+d.substring(d.lastIndexOf("app"), d.length()).replace("app","files");
		String downLoadUrl = "/"+appPath.substring(appPath.lastIndexOf("app"), appPath.length()).replace("app","files");

		downLoadUrl = downLoadUrl+"/"+app.getAppRename();

		if(downLoadUrl.endsWith(".ipa")){
			downLoadUrl = downLoadUrl.replace(".ipa",".plist");
		}
		AppVersionDO appVersionDO = new AppVersionDO(appVersionId,appId, versionName,versionCode, fileSize, 0, 1, 0, 0, "", "", date,versionLoadUrl,d,downLoadUrl);

		appVersionDO.setRecentDownloadTime(date);
//		AppVersionDO appVersionDO = new AppVersionDO();
//		appVersionDO.setAppVersionId(StringUtils.getUUID());
//		appVersionDO.setAppId(appDO.getAppId());
//		appVersionDO.setVersionCode(versionName);
//		appVersionDO.setBuildCode(versionCode);
//		appVersionDO.setAppSizes(fileSize);
//		appVersionDO.setVersionDescription("首次上传");
//		appVersionDO.setCreateTime(date);
//		appVersionDO.setTotalLoadNumber(Integer.valueOf(0));
//		appVersionDO.setUpdateDescription("无");
//		appVersionDO.setDisplayState(Integer.valueOf(1));
//		appVersionDO.setDelFlag(Integer.valueOf(0));
//		appVersionDO.setTodayLoadNumber(Integer.valueOf(0));

		return appVersionDao.save(appVersionDO);

	}
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(APPInfoBean app){

		AppDO appDO = new AppDO();

		AppVersionDO appVersionDO = new AppVersionDO();

		return appDao.update(appDO);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public AppDO getByShortUrl(String shortUrl){

		return appDao.getByShortUrl(shortUrl);
	}
}
