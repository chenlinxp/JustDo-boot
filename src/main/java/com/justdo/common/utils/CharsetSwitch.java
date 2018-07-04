package com.justdo.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;

public final class CharsetSwitch {

	/**
	 * unicode to 汉字
	 * 
	 * @param utfString
	 * @return
	 */
	public static String unicode2word(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		
		while(((i=utfString.indexOf("\\u", pos)) != -1) || ((i=utfString.indexOf("\\U", pos)) != -1)){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}
		
		if(pos < utfString.length()){
			sb.append(utfString.substring(pos, utfString.length()));
		}
		
		return sb.toString();
	}

	/**
	 * 生成消息摘要,
	 * 
	 * @param text
	 *            要加密的文本
	 * @param algorithm
	 *            可选md5,sha,sha-1
	 * @return
	 */
	public static String encrypt(String text, String algorithm) {
		byte[] unencodedText = text.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return text;
		}
		md.reset();
		md.update(unencodedText);
		byte[] encodedText = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedText.length; ++i) {
			if ((encodedText[i] & 0xFF) < 16)
				buf.append("0");
			buf.append(Long.toString(encodedText[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	/**
	 * prefix of ascii string of native character
	 */
	private static String PREFIX = "\\u";

	/**
	 * Native to ascii string. It's same as execut native2ascii.exe.
	 * 
	 * @param str
	 *            native string
	 * @return ascii string
	 */
	public static String native2Ascii(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			sb.append(char2Ascii(chars[i]));
		}
		return sb.toString();
	}

	/**
	 * Native character to ascii string.
	 * 
	 * @param c
	 *            native character
	 * @return ascii string
	 */
	private static String char2Ascii(char c) {
		if (c > 255) {
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			int code = (c >> 8);
			String tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			code = (c & 0xFF);
			tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			return sb.toString();
		} else {
			return Character.toString(c);
		}
	}

	/**
	 * Ascii to native string. It's same as execut native2ascii.exe -reverse.
	 * 
	 * @param str
	 *            ascii string
	 * @return native string
	 */
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	/**
	 * Ascii to native character.
	 * 
	 * @param str
	 *            ascii string
	 * @return native character
	 */
	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	/**
	 * 对字符串进行安全性编码，消除如中文、特殊字符在数据传递过程中的乱码问题， 如通过URL传递中文名称参数是就会遇到接收端出现乱码的问题，可以用此方法解决。
	 * 
	 * @param s
	 * @return 返回安全性编码的字符串
	 */
	public static String encode(String s) {
		if (s == null)
			return "";
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer(i);
		for (int j = 0; j < i; j++) {
			char c;
			if ((c = s.charAt(j)) > '\377') {
				String s1 = Integer.toString(c, 16);
				stringbuffer.append('^');
				for (int k = s1.length(); k < 4; k++)
					stringbuffer.append('0');

				stringbuffer.append(s1);
			} else if (c < '0' || c > '9' && c < 'A' || c > 'Z' && c < 'a' || c > 'z') {
				String s2 = Integer.toString(c, 16);
				stringbuffer.append('~');
				for (int l = s2.length(); l < 2; l++)
					stringbuffer.append('0');
				stringbuffer.append(s2);
			} else {
				stringbuffer.append(s.charAt(j));
			}
		}
		return stringbuffer.toString();
	}

	/**
	 * 对安全性编码了的字符串进行解码处理，还原字符串原始特征。
	 * 
	 * @param s
	 * @return 原始字符串
	 */
	public static String decode(String s) {
		if (s == null)
			return "";
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < i; j++) {
			char c;
			switch (c = s.charAt(j)) {
			case 126: // '~'
				String s1 = s.substring(j + 1, (j + 4) - 1);
				stringbuffer.append((char) Integer.parseInt(s1, 16));
				j += 2;
				break;
			case 94: // '^'
				String s2 = s.substring(j + 1, j + 4 + 1);
				stringbuffer.append((char) Integer.parseInt(s2, 16));
				j += 4;
				break;
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return stringbuffer.toString();
	}

	/**
	 * 对字符串进行encodeURIComponent编码
	 * 
	 * @param input
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:54:41 oxhide
	 */
	public static String encodeURIComponent(String input) {
		if (input == null || "".equals(input)) {
			return input;
		}
		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);
		try {
			for (int i = 0; i < l; i++) {
				String e = input.substring(i, i + 1);
				int c = input.charAt(i);
				UnicodeBlock ub = UnicodeBlock.of(input.charAt(i));
				if (ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
					byte[] b = e.getBytes("utf-8");
					o.append(getHex(b));
					continue;
				}
				o.append(e);
			}
			return o.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * 对字符串进行decodeURIComponent解码
	 * 
	 * @param encodedURI
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:55:13 oxhide
	 */
	public static String decodeURIComponent(String encodedURI) {
		char actualChar;
		StringBuffer buffer = new StringBuffer();
		int bytePattern, sumb = 0;
		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);
			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = (hb << 4) | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}
			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取16字符串
	 * 
	 * @param buf
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:54:56 oxhide
	 */
	private static String getHex(byte buf[]) {
		StringBuilder o = new StringBuilder(buf.length * 3);
		for (int i = 0; i < buf.length; i++) {
			int n = buf[i] & 0xff;
			o.append("%");
			if (n < 0x10)
				o.append("0");
			o.append(Long.toString(n, 16).toUpperCase());
		}
		return o.toString();
	}

	/**
	 * 异或加密
	 * 
	 * @param text
	 * @param key
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:02:51 oxhide
	 */
	public static String enLargeCode(String text, String key, String charset) {
		StringBuffer result = new StringBuffer();
		byte[] strBuf = null;
		try {
			strBuf = text.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] keyBuf = null;
		try {
			keyBuf = key.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int c = 0;
		int z = keyBuf.length;
		for (int i = 0; i < strBuf.length; i++) {
			byte bS = strBuf[i];
			byte bK = keyBuf[c];
			byte bO = (byte) (bS ^ bK);
			c = (c + 1) % z;
			result.append(BytetoHex(bO));
		}
		return result.toString();
	}

	/**
	 * 异或解密
	 * 
	 * @param text
	 * @param key
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:04:11 oxhide
	 */
	public static String deLargeCode(String text, String key, String charset) {
		String result = null;
		byte[] strBuf = HextoByte(text);
		byte[] keyBuf = null;
		try {
			keyBuf = key.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int c = 0;
		int z = keyBuf.length;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(strBuf.length);
		result = "";
		for (int i = 0; i < strBuf.length; i++) {
			byte bS = strBuf[i];
			byte bK = keyBuf[c];
			byte bO = (byte) (bS ^ bK);
			c = (c + 1) % z;
			baos.write(bO);
		}
		try {
			baos.flush();
			result = baos.toString(charset);
			baos.close();
			baos = null;
		} catch (IOException io) {
			io.getStackTrace();
		}
		return result;
	}

	/**
	 * byte转换16进制
	 * 
	 * @param b
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:02:58 oxhide
	 */
	private static final String BytetoHex(byte b) {
		return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF".charAt(b & 0xf));
	}

	/**
	 * char 转换 byte
	 * 
	 * @param c
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:04:19 oxhide
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 16进制转换byte数组
	 * 
	 * @param hexString
	 * @return Administrator com.sxsihe.utils.common CharsetSwitch.java 2012下午3:04:31 oxhide
	 */
	public static final byte[] HextoByte(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
}
