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
	public AppDO getByBundleId(String bundleId){
		return appDao.get(bundleId);
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
		String bundleName = app.getBundleName();
		String fileSize = app.getFileSize();
		Date date = new Date();
		AppDO appDO = this.getByBundleId(app.getPackageName());
		if(appDO == null) {
			appDO.setBundleName(bundleName);
			appDO.setBundleId(pageName);
			appDO.setAppKey(StringUtils.getUUID());
			appDO.setAppId(StringUtils.getUUID());
			String loadUrl = "/appmanage/app/download/"+appDO.getAppId();
			appDO.setLoadUrl(loadUrl);
			appDO.setCodeQrA("暂无");
			appDO.setCodeQrB("暂无");
			appDO.setCodeQrC("暂无");
			appDO.setIsCombine(Integer.valueOf(2));
			appDO.setCombineAppId("");
			appDO.setIconUrl(logoImage);
			appDO.setCreateTime(date);
			appDO.setModifyTime(date);
			//一个月的有效期
			appDO.setExpirerTime(DateUtils.addMonth(date,1));
			String shortUrl = StringUtils.shortUrl(loadUrl);
			appDO.setShortUrl(shortUrl);
			shortUrl = justdoConfig.getBaseAddress() + shortUrl;
			String qRCodeUrl = logoImage.substring(0, logoImage.lastIndexOf("/"))+"/";

		String a =	QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,250,app.getLogoImage());
		String b =  QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,500,app.getLogoImage());
		String c =  QRCodeUtils.encodeZxingCode(shortUrl,qRCodeUrl,800,app.getLogoImage());

			appDO.setCodeQrA(a);
			appDO.setCodeQrB(b);
			appDO.setCodeQrC(c);
		}else{
			appDO.setIconUrl(app.getLogoImage());
		}


		AppVersionDO appVersionDO = new AppVersionDO();
		appVersionDO.setAppId(appDO.getAppId());
		appVersionDO.setVersionCode(versionName);
		appVersionDO.setBuildCode(versionCode);
		appVersionDO.setAppSizes(fileSize);
		appVersionDO.setVersionDescription("首次上传");
		appVersionDO.setCreateTime(date);
		appVersionDO.setTotalLoadNumber(Integer.valueOf(0));
		appVersionDO.setUpdateDescription("无");
		appVersionDO.setDisplayState(Integer.valueOf(1));
		appVersionDO.setDelFlag(Integer.valueOf(0));
		appVersionDO.setTodayLoadNumber(Integer.valueOf(0));


		appDao.save(appDO);
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
