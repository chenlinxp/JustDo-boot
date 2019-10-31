package com.justdo.common.utils;

import java.io.File;
import java.util.UUID;

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
}
