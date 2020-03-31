package com.justdo.common.utils;

import com.justdo.common.domain.ApkInfo;
import com.justdo.common.domain.ImpliedFeature;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/18 下午6:40
 */
public class ApkUtils {

	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String SDK_VERSION = "sdkVersion";
	public static final String TARGET_SDK_VERSION = "targetSdkVersion";
	public static final String USES_PERMISSION = "uses-permission";
	public static final String APPLICATION_LABEL = "application-label";
	public static final String APPLICATION_ICON = "application-icon";
	public static final String USES_FEATURE = "uses-feature";
	public static final String USES_IMPLIED_FEATURE = "uses-implied-feature";
	public static final String SUPPORTS_SCREENS = "supports-screens";
	public static final String SUPPORTS_ANY_DENSITY = "supports-any-density";
	public static final String DENSITIES = "densities";
	public static final String PACKAGE = "package";
	public static final String APPLICATION = "application:";
	public static final String LAUNCHABLE_ACTIVITY = "launchable-activity";
	private static Logger logger = Logger.getLogger(ApkUtils.class);
	private ProcessBuilder mBuilder = new ProcessBuilder(new String[0]);
	private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
	private static final String FEATURE_SPLIT_REGEX = "(:')|(',')|'";

	public ApkUtils() {
		this.mBuilder.redirectErrorStream(true);
	}

	private void setApkInfoProperty(ApkInfo apkInfo, String source) {
		if(source.startsWith("package")) {
			this.splitPackageInfo(apkInfo, source);
		} else if(source.startsWith("launchable-activity")) {
			apkInfo.setLaunchableActivity(this.getPropertyInQuote(source));
		} else if(source.startsWith("sdkVersion")) {
			apkInfo.setSdkVersion(this.getPropertyInQuote(source));
		} else if(source.startsWith("targetSdkVersion")) {
			apkInfo.setTargetSdkVersion(this.getPropertyInQuote(source));
		} else if(source.startsWith("uses-permission")) {
			apkInfo.addToUsesPermissions(this.getPropertyInQuote(source));
		} else if(source.startsWith("application-label")) {
			apkInfo.setApplicationLable(this.getPropertyInQuote(source));
		} else if(source.startsWith("application-icon")) {
			apkInfo.addToApplicationIcons(this.getKeyBeforeColon(source), this.getPropertyInQuote(source));
		} else if(source.startsWith("application:")) {
			String[] rs = source.split("( icon=')|'");
			apkInfo.setApplicationIcon(rs[rs.length - 1]);
			apkInfo.setApplicationLable(rs[1]);
		} else if(source.startsWith("uses-feature")) {
			apkInfo.addToFeatures(this.getPropertyInQuote(source));
		} else if(source.startsWith("uses-implied-feature")) {
			apkInfo.addToImpliedFeatures(this.getFeature(source));
		}

	}

	private ImpliedFeature getFeature(String source) {
		String[] result = source.split("(:')|(',')|'");
		ImpliedFeature impliedFeature = new ImpliedFeature(result[1], result[2]);
		return impliedFeature;
	}

	private String getPropertyInQuote(String source) {
		int index = source.indexOf("'") + 1;
		return source.substring(index, source.indexOf(39, index));
	}

	private String getKeyBeforeColon(String source) {
		return source.substring(0, source.indexOf(58));
	}

	private void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
		String[] packageInfo = packageSource.split("(: )|(=')|(' )|'");
		apkInfo.setPackageName(packageInfo[2]);
		apkInfo.setVersionCode(packageInfo[4]);
		apkInfo.setVersionName(packageInfo[6]);
	}

	private final void closeIO(Closeable c) {
		if(c != null) {
			try {
				c.close();
			} catch (IOException var3) {
				var3.printStackTrace();
			}
		}

	}

	public ApkInfo getApkInfo(String mAaptPath, String apkPath) throws Exception {
		Process process = this.mBuilder.command(new String[]{mAaptPath, "d", "badging", apkPath}).start();
		InputStream is = null;
		is = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
		String tmp = br.readLine();

		try {
			if(tmp == null || !tmp.startsWith("package")) {
				logger.error("----------------参数不正确，无法正常解析APK包。输出结果为:\n" + tmp + "...");
				throw new Exception("参数不正确，无法正常解析APK包。输出结果为:\n" + tmp + "...");
			} else {
				ApkInfo apkInfo = new ApkInfo();

				do {
					this.setApkInfoProperty(apkInfo, tmp);
				} while((tmp = br.readLine()) != null);

				ApkInfo var9 = apkInfo;
				return var9;
			}
		} catch (Exception var12) {
			throw var12;
		} finally {
			process.destroy();
			this.closeIO(is);
			this.closeIO(br);
		}
	}
}

