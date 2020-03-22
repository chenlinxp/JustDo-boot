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
		logoImage =	"/"+logoImage.substring(logoImage.lastIndexOf("app"), logoImage.length()).replace("app","files");
		String bundleName = app.getBundleName();
		String fileSize = app.getFileSize();
		String appId = StringUtils.getUUID();
//		a = a.substring(a.lastIndexOf("app"), a.length()).replace("app","files");
//		b = b.substring(b.lastIndexOf("app"), b.length()).replace("app","files");
//		c = c.substring(c.lastIndexOf("app"), c.length()).replace("app","files");

		Date date = new Date();
		Map<String, Object> map = new HashedMap();
		map.put("bundleId",app.getPackageName());
		map.put("appType",app.getAppType());
		AppDO appDO = this.getByBundleId(map);
		if(appDO == null) {


			String appKey = StringUtils.getUUID();
			String loadUrl = "/appmanage/app/download/"+appId;
			String qRCodeUrl = logoImage.substring(0, logoImage.lastIndexOf("/"))+"/";
			String shortUrl = StringUtils.shortUrl(loadUrl);
			shortUrl = justdoConfig.getBaseAddress() + shortUrl;
			String a =	QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,250,app.getLogoImage());
			String b =  QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,500,app.getLogoImage());
			String c =  QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,800,app.getLogoImage());
			appDO = new AppDO(appId,appKey, appName, bundleName, pageName, shortUrl, loadUrl, DateUtils.addMonth(date,1), app.getAppType(), 0, "", null, logoImage, a, b, c, "", date, date);

//			appDO.setBundleName(bundleName);
//			appDO.setBundleId(pageName);
//			appDO.setAppKey(StringUtils.getUUID());
//			appDO.setAppId(appId);
//
//			appDO.setLoadUrl(loadUrl);
//			appDO.setCodeQrA("暂无");
//			appDO.setCodeQrB("暂无");
//			appDO.setCodeQrC("暂无");
//			appDO.setIsCombine(Integer.valueOf(2));
//			appDO.setCombineAppId("");
//			appDO.setIconUrl(logoImage);
//			appDO.setCreateTime(date);
//			appDO.setModifyTime(date);
//			//一个月的有效期
//			appDO.setExpirerTime(DateUtils.addMonth(date,1));
//
//			appDO.setShortUrl(shortUrl);

//			appDO.setCodeQrA(a);
//			appDO.setCodeQrB(b);
//			appDO.setCodeQrC(c);
			appDao.save(appDO);

		}else{
			appId = appDO.getAppId();
			appDO.setIconUrl(logoImage);
			appDao.update(appDO);
		}

        String  appVersionId = StringUtils.getUUID();
		AppVersionDO appVersionDO = new AppVersionDO(appVersionId,appId, versionName,versionCode, fileSize, 0, 1, 0, 0, "", "", date);

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
}
