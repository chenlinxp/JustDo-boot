package com.justdo.common.utils;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
	/**
	 * 返回拼音,忽略多音字,只取第一个
	 * @param src 	源
	 * @param isAll是否是全拼
	 * @param isUpper是否是大写
	 * @return
	 */
	public static String getPinyin(String src,boolean isAll,boolean isUpper){
		if(StringUtils.isNotEmpty(src)){
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			hanYuPinOutputFormat.setCaseType(isUpper ? HanyuPinyinCaseType.UPPERCASE : HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			
			char[] chars = src.toCharArray();
			String[][] temp = new String[src.length()][];
			int i = 0;
			for(char c : chars){
				try {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(c, hanYuPinOutputFormat);
					if(result!= null && !isAll){
						String[] r2 = new String[result.length];
						int j = 0;
						for(String s : result){
							r2[j] = s.substring(0,1);
							j++;
						}
						result = r2;
					}
					temp[i] = result == null ? new String[]{String.valueOf(c)} : result;
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
				i++;
			}
			int j = temp[0].length;
			if(j > 0){
				j = temp.length;
				StringBuilder sb = new StringBuilder();
				for(i = 0;i<j;i++) sb.append(temp[i][0]);
				return sb.toString();
			}
		}
		return new String();
	}
	
	/**
	 * 返回拼音数组,多音字在数组中
	 * @param src 	源
	 * @param isAll是否是全拼
	 * @param isUpper是否是大写
	 * @return
	 */
	public static List getPinyins(String src,boolean isAll,boolean isUpper){
		if(StringUtils.isNotEmpty(src)){
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			hanYuPinOutputFormat.setCaseType(isUpper ? HanyuPinyinCaseType.UPPERCASE : HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
			
			char[] chars = src.toCharArray();
			String[][] temp = new String[src.length()][];
			int i = 0;
			for(char c : chars){
				try {
					String[] result = PinyinHelper.toHanyuPinyinStringArray(c, hanYuPinOutputFormat);
					if(result != null && !isAll){
						String[] r2 = new String[result.length];
						int j = 0;
						for(String s : result){
							r2[j] = s.substring(0,1);
							j++;
						}
						result = r2;
					}
					temp[i] = result == null ? new String[]{String.valueOf(c)} : result;
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
				i++;
			}
			String[] array =DoExchange(temp)[0];
			List result = new ArrayList();
			int j = 0;
			for(i = 0,j = array.length;i <j;i++){
				if(result.indexOf(array[i]) == -1)
					result.add(array[i]);
			}
			return result;
		}
		return new ArrayList();
	}
	

	/**
	 * 递归
	 * 
	 * @author wyh
	 * @param strJaggedArray
	 * @return
	 */
	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}
}
