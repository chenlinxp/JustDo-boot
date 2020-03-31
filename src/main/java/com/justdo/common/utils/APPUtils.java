package com.justdo.common.utils;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.justdo.common.domain.APPInfoBean;
import com.justdo.common.domain.ApkInfo;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 功能描述:APP 安装包的处理：APK、IPA
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/15 下午9:44
 */
public class APPUtils {

	/**
	 * 读取apk
	 * @param file
	 * @return APPInfoBean
	 */
	public static APPInfoBean readAPK(File file,String iconUrl,String aaptPath){

		APPInfoBean aPPInfoBean = new APPInfoBean();

		try {

			ApkFile apkFile = new ApkFile(file);

			ApkMeta apkMeta = apkFile.getApkMeta();

//			String xml = apkFile.getManifestXml();

//			System.out.println(xml);

			aPPInfoBean.setBundleName(apkMeta.getLabel());
			aPPInfoBean.setAppName(apkMeta.getLabel());

//			aPPInfoBean.setVersionCode(apkMeta.getVersionCode().toString());
//			aPPInfoBean.setPackageName(apkMeta.getPackageName());
//			aPPInfoBean.setVersionName(apkMeta.getVersionName());

			aPPInfoBean.setFileSize(String.valueOf(file.length() / 1024L / 1024L));



			ApkInfo apkInfo = (new ApkUtils()).getApkInfo(aaptPath, file.getPath());
//			aPPInfoBean.setAppName(apkInfo.getApplicationLable());
//			aPPInfoBean.setBundleName(apkInfo.getApplicationLable());
			aPPInfoBean.setPackageName(apkInfo.getPackageName());
			aPPInfoBean.setVersionName(apkInfo.getVersionName());
			aPPInfoBean.setVersionCode(apkInfo.getVersionCode());
			aPPInfoBean.setAppType(1);
			String iconName = apkInfo.getApplicationIcon();

			iconUrl = iconUrl+apkInfo.getApplicationLable();
			File  icond = new File(iconUrl);
			if(!icond.exists()){
				icond.mkdirs();
			}
			iconUrl = iconUrl+"/icon.png";
			aPPInfoBean.setLogoImage(iconUrl);
			//提取icon图片开始
			InputStream is = extractFileFromApk(file.getPath(),iconName);
			File iconFile = new File(iconUrl);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(iconFile), 1024);
			byte[] b = new byte[1024];

			BufferedInputStream bis = new BufferedInputStream(is, 1024);

			while(bis.read(b) != -1) {
				bos.write(b);
			}
			bos.flush();
			is.close();
			bis.close();
			bos.close();
			//提取icon图片结束

		} catch (Exception e) {
			file.delete();
			System.out.println("读取apk文件失败"+e.getMessage());
		}
		return aPPInfoBean;
	}

	/**
	 * 提取文件
	 * @param apkPath
	 * @param fileName
	 * @param outputPath
	 * @throws Exception
	 */
	public static void extractFileFromApk(String apkPath, String fileName, String outputPath) throws Exception {


		InputStream is = extractFileFromApk(apkPath, fileName);
		File file = new File(outputPath);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 1024);
		byte[] b = new byte[1024];

		BufferedInputStream bis = new BufferedInputStream(is, 1024);

		while(bis.read(b) != -1) {
			bos.write(b);
		}
		bos.flush();
		is.close();
		bis.close();
		bos.close();
	}

	/**
	 *
	 * @param apkPath
	 * @param fileName
	 * @return
	 */
	public static InputStream extractFileFromApk(String apkPath, String fileName) {

		try {
			ZipFile zFile = new ZipFile(apkPath);
			ZipEntry entry = zFile.getEntry(fileName);
			entry.getComment();
			entry.getCompressedSize();
			entry.getCrc();
			entry.isDirectory();
			entry.getSize();
			entry.getMethod();
			InputStream stream = zFile.getInputStream(entry);
			return stream;
		} catch (IOException var5) {
			var5.printStackTrace();
			return null;
		}
	}

	/**
	 * 读取ipa
	 * @param file
	 * @return APPInfoBean
	 */
	public static APPInfoBean readIPA(File file,String iconUrl,String pythonShellPath){

		String newIconName = "";
		APPInfoBean aPPInfoBean = new APPInfoBean();
		try {
			InputStream is = new FileInputStream(file);
			InputStream is2 = new FileInputStream(file);
			ZipInputStream zipIns = new ZipInputStream(is);
			ZipInputStream zipIns2 = new ZipInputStream(is2);
			ZipEntry ze;
			ZipEntry ze2;
			InputStream infoIs = null;
			NSDictionary rootDict = null;
			String icon = null;
			while ((ze = zipIns.getNextEntry()) != null) {
				if (!ze.isDirectory()) {
					String name = ze.getName();
					if (null != name &&
							name.toLowerCase().contains(".app/info.plist")) {
						ByteArrayOutputStream _copy = new
								ByteArrayOutputStream();
						int chunk = 0;
						byte[] data = new byte[1024];
						while(-1!=(chunk=zipIns.read(data))){
							_copy.write(data, 0, chunk);
						}
						infoIs = new ByteArrayInputStream(_copy.toByteArray());
						rootDict = (NSDictionary) PropertyListParser.parse(infoIs);

						//我们可以根据info.plist结构获取任意我们需要的东西
						//比如下面我获取图标名称，图标的目录结构请下面图片
						//获取图标名称
						NSDictionary iconDict = (NSDictionary) rootDict.get("CFBundleIcons");

						while (null != iconDict) {
							if(iconDict.containsKey("CFBundlePrimaryIcon")){
								NSDictionary CFBundlePrimaryIcon = (NSDictionary)iconDict.get("CFBundlePrimaryIcon");
								if(CFBundlePrimaryIcon.containsKey("CFBundleIconFiles")){
									NSArray CFBundleIconFiles =(NSArray)CFBundlePrimaryIcon.get("CFBundleIconFiles");
									icon = CFBundleIconFiles.getArray()[0].toString();
									if(icon.contains(".png")){
										icon = icon.replace(".png", "");
									}
									System.out.println("获取icon名称:" + icon);
									break;
								}
							}
						}
						break;
					}
				}
			}
			// 应用名称
			NSString parameters = (NSString)rootDict.get("CFBundleName");
			aPPInfoBean.setAppName(parameters.toString());

			aPPInfoBean.setBundleName(parameters.toString());
			iconUrl = iconUrl+parameters.toString();
			String tmpIconUrl = iconUrl+"/tmp";
			File  iconfile = new File(tmpIconUrl);
			if(!iconfile.exists()){
				iconfile.mkdirs();
			}
			tmpIconUrl =  tmpIconUrl+"/icon.png";
			iconUrl = iconUrl+"/icon.png";

			//根据图标名称下载图标文件到指定位置
			while ((ze2 = zipIns2.getNextEntry()) != null) {
				if (!ze2.isDirectory()) {
					String name = ze2.getName();
					System.out.println(name+"--------");
					if(name.contains(icon.trim())){

						FileOutputStream fos = new FileOutputStream(new File(tmpIconUrl));
						int chunk = 0;
						byte[] data = new byte[1024];
						while(-1!=(chunk=zipIns2.read(data))){
							fos.write(data, 0, chunk);
						}
						fos.close();
						break;
					}
				}
			}

			////////////////////////////////////////////////////////////////
			//如果想要查看有哪些key ，可以把下面注释放开
//            for (String keyName : rootDict.allKeys()) {
//				System.out.println(keyName + ":" + rootDict.get(keyName).toString());
//			  }


			// 应用包名
			parameters = (NSString) rootDict.get("CFBundleIdentifier");
			aPPInfoBean.setPackageName(parameters.toString());

			// 应用版本名
			parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
			aPPInfoBean.setVersionName(parameters.toString());

			//应用版本号
			parameters = (NSString) rootDict.get("CFBundleVersion");

			aPPInfoBean.setVersionCode(parameters.toString());

			aPPInfoBean.setFileSize(String.valueOf(file.length() / 1024L / 1024L));
			aPPInfoBean.setAppType(2);
			aPPInfoBean.setLogoImage(iconUrl);
			/////////////////////////////////////////////////
			infoIs.close();
			is.close();
			zipIns.close();


		} catch (Exception e) {
			file.delete();
			System.out.println("读取ipa文件失败"+e.getMessage());
		}
		System.out.println("================================执行命令获取icon开始=========================");
		Process process;
		try {
			process = Runtime.getRuntime().exec("sh "+pythonShellPath+"ipin.sh"+" "+pythonShellPath+" "+aPPInfoBean.getAppName());
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("================================执行命令获取icon结束=========================");
		System.out.println("================================图标名称："+newIconName+"=========================");
		return aPPInfoBean;
	}

	public static String createPlist(String filePath,APPInfoBean aPPInfoBean,String baseAddress) throws IOException {
		System.out.println("==========开始创建plist文件");
		String ipaPath = filePath.substring(filePath.lastIndexOf("app"), filePath.length());
		String plistPath = filePath.substring(0, filePath.lastIndexOf("."));
		plistPath = plistPath + ".plist";
        String logoImage = aPPInfoBean.getLogoImage();
		logoImage = logoImage.substring(logoImage.lastIndexOf("app"), logoImage.length());
		File file = new File(plistPath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException var12) {
				var12.printStackTrace();
			}
		}

		String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				      +"<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
				      +"<plist version=\"1.0\">\n"
				      +"<dict>\n"
				      +"<key>items</key>\n"
				      +"<array>\n<dict>\n<key>assets</key>\n<array>\n"
				      +"<dict>\n<key>kind</key>\n<string>software-package</string>\n<key>url</key>\n"
				      +"<string>"+ baseAddress  + ipaPath.replace("app","files") + "</string>\n"
				      +"</dict>\n"
				      +"<dict>\n"
				      +"<key>kind</key>\n"
				      +"<string>full-size-image</string>\n"
				      +"<key>needs-shine</key>\n"
				      +"<true/>\n" + "<key>url</key>\n"
				      +"<string>"+ baseAddress + logoImage.replace("app","files") + "</string>\n"
				      +"</dict>\n"
				      +"<dict>\n"
				      +"<key>kind</key>\n"
				      +"<string>display-image</string>\n"
				      +"<key>needs-shine</key>\n" + "<true/>\n"
				      +"<key>url</key>\n"
				      +"<string>" +baseAddress + logoImage.replace("app","files") + "</string>\n"
				      +"</dict>\n" + "</array>\n"
				      + "<key>metadata</key>\n"
				      + "<dict>\n"
				      + "<key>bundle-identifier</key>\n"
				      + "<string>" + aPPInfoBean.getPackageName() + "</string>\n"
				      + "<key>bundle-version</key>\n" + "<string>" + aPPInfoBean.getVersionName()+ "</string>\n"
				      + "<key>kind</key>\n" + "<string>software</string>\n"
				      + "<key>subtitle</key>\n" + "<string></string>\n"
				      + "<key>title</key>\n" + "<string>" + aPPInfoBean.getAppName() + "</string>\n"
				      + "</dict>\n"
				      + "</dict>\n"
				      + "</array>\n"
				      + "</dict>\n"
				      + "</plist>\n";

		try {
			FileOutputStream output = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
			writer.write(plist);
			writer.close();
			output.close();
		} catch (Exception var11) {
			System.err.println("==========创建plist文件异常：" + var11.getMessage());
		}

		System.out.println("==========成功创建plist文件");
		return plistPath;
	}

	public static void main(String[] args) {

		System.out.println("======apk=========");
		String apkiconurl = "/Users/chenlin/Documents/GitHub/app/apk/";
		String apkUrl = "/Users/chenlin/Documents/GitHub/BHAF2.6.1.apk";
		File file = new File(apkUrl);
        String aaptPath = "/Users/chenlin/Library/Android/sdk/build-tools/26.0.0/aapt";
		APPInfoBean aPPInfoBean = readAPK(file,apkiconurl,aaptPath);
		System.out.println(JSONUtils.beanToJson(aPPInfoBean));



		System.out.println("======ipa==========");
		String ipaiconurl = "/Users/chenlin/Documents/GitHub/app/ipa/";
		String ipaUrl = "/Users/chenlin/Documents/GitHub/BHAF2.6.1.ipa";
		String pythonShellPath = "/Users/chenlin/Documents/GitHub/app/ipin.sh";
		File file2 = new File(ipaUrl);
		aPPInfoBean = readIPA(file2,ipaiconurl,pythonShellPath);
		System.out.println(JSONUtils.beanToJson(aPPInfoBean));
	}

}
