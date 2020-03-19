package com.justdo.common.utils;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.justdo.common.domain.APPInfoBean;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

//	public static void uploadFile(byte[] bfile, File targetFile, String fileName) throws Exception {
//		if (!targetFile.exists()) {
//			targetFile.mkdirs();
//		}
//
//		FileOutputStream out = new FileOutputStream(targetFile.getPath()+"/"+fileName);
//		out.write(bfile);
//		out.flush();
//		out.close();
//	}


	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public static String getFileTypeByName(String fileName)
	{
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	* 读取apk
	* @param file
	* @return APPInfoBean
	*/
	public static APPInfoBean readAPK(File file){

		APPInfoBean aPPInfoBean = new APPInfoBean();

			try {
				ApkFile apkFile = new ApkFile(file);
				ApkMeta apkMeta = apkFile.getApkMeta();

				aPPInfoBean.setAppName(apkMeta.getLabel());
				aPPInfoBean.setBundleName(apkMeta.getLabel());
				aPPInfoBean.setVersionCode(apkMeta.getVersionCode().toString());
				aPPInfoBean.setPackageName(apkMeta.getPackageName());
				aPPInfoBean.setFileSize(String.valueOf(file.length() / 1024L / 1024L));

			} catch (Exception e) {
              System.out.println("读取apk文件失败"+e.getMessage());
			}
			return aPPInfoBean;
	}
	/**
	* 读取ipa
	* @param file
	* @return APPInfoBean
	*/
	public static APPInfoBean readIPA(File file){
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

				//根据图标名称下载图标文件到指定位置
				while ((ze2 = zipIns2.getNextEntry()) != null) {
					if (!ze2.isDirectory()) {
						String name = ze2.getName();
						System.out.println(name);
						if(name.contains(icon.trim())){
							System.out.println(11111);
							FileOutputStream fos = new FileOutputStream(new File("E:\\python\\img\\icon.png"));
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

				// 应用名称
				NSString parameters = (NSString)rootDict.get("CFBundleName");
				aPPInfoBean.setAppName(parameters.toString());

				aPPInfoBean.setBundleName(parameters.toString());
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
				/////////////////////////////////////////////////
				infoIs.close();
				is.close();
				zipIns.close();

			} catch (Exception e) {
				System.out.println("读取apk文件失败"+e.getMessage());
			}
			return aPPInfoBean;
		}

	public static void main(String[] args) {
			System.out.println("======apk=========");
			String apkUrl = "/Users/chenlin/Documents/GitHub/BHAF2.6.1.apk";
			File file = new File(apkUrl);
		    APPInfoBean aPPInfoBean = readAPK(file);

		    System.out.println(JSONUtils.beanToJson(aPPInfoBean));

			System.out.println("======ipa==========");
			String ipaUrl = "/Users/chenlin/Documents/GitHub/BHAF2.6.1.ipa";
		    File file2 = new File(ipaUrl);
		    aPPInfoBean = readIPA(file2);
		    System.out.println(JSONUtils.beanToJson(aPPInfoBean));
		}

}
