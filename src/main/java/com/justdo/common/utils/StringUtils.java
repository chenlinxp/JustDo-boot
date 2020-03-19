package com.justdo.common.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author justdo
 */


public class StringUtils extends org.apache.commons.lang.StringUtils {
    private final static Map<String, String> regexs = new HashMap<String, String>();

    static{
        Properties entityReferences = new Properties();
        InputStream is = StringUtils.class.getResourceAsStream("regex.properties");
        if (is == null){
            throw new IllegalStateException("Cannot find reference definition file [regex.properties] as class path resource");
        }
        try {
            try {
                entityReferences.load(is);
            } finally {
                is.close();
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to parse reference definition file [regex.properties]: " + ex.getMessage());
        }
        Enumeration<?> e = entityReferences.propertyNames();
        String key,value;
        for (; e.hasMoreElements(); regexs.put(key, value)) {
            key = (String) e.nextElement();
            value = StringUtils.trim(entityReferences.getProperty(key));
        }
    }

    /**
     * split掉textarea的文本
     * @param a
     * @return
     */
    public static String[] splitTextArea(String a){
        String[] aa = org.apache.commons.lang.StringUtils.split(a, "\r\n");
        return aa;
    }

    /**
     * 验证src是否满足某个内置正则表达式
     * @param src
     * @param type 内置:email(邮箱)、upperCase(大写字母)、lowerCase(小写字母)、chinese(中文)、url(http地址)、ip、zoneCode(区号)、qq、alpha(数字、字母、下划线)、phone、fax(传真)、number(数字)、int、float、plus(正数)、plusInt(正整数)、plusFloat(正浮点数)、unPlus(非正数)、unPlusInt(非正整数)、unPlusFloat(非正浮点数)、minus(负数)、minusInt(负整数)、minusFloat(负浮点数)、unMinus(非负数)、unMinsInt(非负整数)、unMinusFloat(非负浮点数)；可以通过regex.properties扩展
     * @return
     */
    public static boolean checkType(Object src,String type){
        type = regexs.get(type);
        if(type == null) return true;
        if(src == null) return false;
        return regex(type,src.toString());
    }

    /**
     * 替换正则表达式
     * @param src
     * @param reg
     * @param with
     * @return
     */
    public static String replaceReg(String src,String reg,String with){
        if(StringUtils.isBlank(src)) {
            return src;
        }
        return src.replaceAll(reg, with);
    }

    /**
     * 字符串数组转为list集合
     * @param src
     * @return
     */
    public static List<String> toList(String[] src){
        List<String> list = new ArrayList<String>();
        for(String s : src){ list.add(s);}
        return list;
    }
    /**
     * 字符串拼接 例如 stringBuild("高级黑啊{0},太原热{1}",new String[]{"bay","book"});
     *
     * @param src
     * @param args
     * @return
     */
    public static String stringBuild(String src, String... args) {
        for (int i = 0, j = args.length; i < j; i++) {

        src = src.replaceAll("\\{" + i + "\\}", args[i] + "");
        }
        return src;
    }
    /**
     * 字符串拼接 例如 stringBuild("高级黑啊{name},太原热{age}",{"name":"cc","age":"20"});
     *
     * @param src
     * @param args
     * @return
     */
//    public static String stringBuild(String src, Map<String,Object> args) {
//        String def = new String();
//        Pattern p = Pattern.compile("\\{([^\\}]+)\\}");
//        Matcher m = p.matcher(src);
//        StringBuffer buffer = new StringBuffer();
//        while (m.find()) {
//            m.appendReplacement(buffer, MapUtils.get(args, m.group(1), def).toString());
//        }
//        m.appendTail(buffer);
//        return buffer.toString();
//    }
    /**
     * 字符串拼接 例如 stringBuild("高级黑啊{name},太原热{age}",{"name":"cc","age":"20"});
     * @param src
     * @param args
     * @param skipIfNotExist
     * @return
     */
//    public static String stringBuild(String src, Map<String,Object> args,boolean skipIfNotExist) {
//        String def = new String();
//        Pattern p = Pattern.compile("\\{([^\\}]+)\\}");
//        Matcher m = p.matcher(src);
//        StringBuffer buffer = new StringBuffer();
//        while (m.find()) {
//            String key = m.group(1);
//            Object v = MapUtils.get(args, key, null);
//            if(v == null) {
//                if(skipIfNotExist) continue;
//                else  v = def;
//            }
//            m.appendReplacement(buffer, v.toString());
//        }
//        m.appendTail(buffer);
//        return buffer.toString();
//    }

    /**
     * 字符串拼接 例如 stringBuild("高级黑啊{name},太原热{age}",{"name":"cc","age":"20"});
     * @param src
     * @param args
     * @param skipIfNotExist
     * @param no_r_n 是否屏蔽换行符
     * @return
     */
//    public static String stringBuild(String src, Map<String,Object> args,boolean skipIfNotExist,boolean no_r_n){
//        String def = new String();
//        Pattern p = Pattern.compile("\\{([^\\}]+)\\}");
//        Matcher m = p.matcher(src);
//        StringBuffer buffer = new StringBuffer();
//        while (m.find()) {
//            String key = m.group(1);
//            Object v = MapUtils.get(args, key, null);
//            if(v == null) {
//                if(skipIfNotExist) continue;
//                else  v = def;
//            }
//            if(no_r_n){
//                m.appendReplacement(buffer, v.toString().replaceAll("\r", "").replaceAll("\n", ""));
//            }else{
//                m.appendReplacement(buffer, v.toString());
//            }
//        }
//        m.appendTail(buffer);
//        return buffer.toString();
//    }

    public static String toString(String args) {
        if (args != null)
            return args;
        return "";
    }

    /**
     * 正则表达式是否符合
     *
     * @param regex
     * @param value
     * @return
     */
    public static boolean regex(String regex, String value) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 获取字符串对正则表达式第一个匹配字符
     * @param regex 正则表达式
     * @param value 元字符串
     * @return
     */
    public static String regexGet(String regex, String value){
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            if(m.find()){
                return m.group();
            }
        } catch (Exception e) {
        }

        return null;
    }
    /**
     * 获取字符串对正则表达式第一个匹配字符
     * @param regex 正则表达式
     * @param value 元字符串
     * @param index 第几组,从0开始,如/\{([^}]+)\}/g 中有两组
     * @return
     */
    public static String regexGet(String regex, String value,int index){
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            if(m.find()){
                return m.group(index);
            }
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 返回字符串对正则表达式所有匹配字符
     * @param regex 正则表达式
     * @param value 元字符串
     * @return
     */
    public static List<String> regexGets(String regex, String value){
        List<String> result = new ArrayList<String>();
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            while (m.find()) {
                result.add(m.group());
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 返回二维数组
     * @param regex
     * @param value
     * @return
     */
    public static List<List<String>> regexGetGroups(String regex, String value){
        List<List<String>> result = new ArrayList<List<String>>();
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            List<String> temp = null;
            while (m.find()) {
                temp = new ArrayList<String>();
                int j = m.groupCount();
                for(int i = 0 ;i <= j; i++){
                    temp.add(m.group(i));
                }
                result.add(temp);
            }
        } catch (Exception e) {
        }
        return result;
    }
    /**
     *
     * 返回字符串对正则表达式所有匹配字符
     * @param regex 正则表达式
     * @param value 元字符串
     * @param index 第几组,从0开始,如/\{([^}]+)\}/g 中有两组
     * @return
     */
    public static List<String> regexGets(String regex, String value,int index){
        List<String> result = new ArrayList<String>();
        try {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            while (m.find()) {
                result.add(m.group(index));
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 获取uuid
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 校验sql语句
     * @param str
     * @return
     */
    public static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获得byte[]型的str
     * @param str
     * @return
     */
    public static byte[] getByteString(String str){
            return str.getBytes();
    }

    /**
     * 短链接
     * @param url
     * @return
     */
    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "justdo";

        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"

        };
        String sMD5EncryptResult = MD5Utils.encrypt(key + url);
        String resUrl = "";

        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = sMD5EncryptResult.substring(2 * 8, 2 * 8 + 8); //固定取第三组

        // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
        long lHexLong = 1073741823L & Long.parseLong(sTempSubString, 16);
        String outChars = "";

        for(int j = 0; j < 6; ++j) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 61L & lHexLong;
            // 把取得的字符相加
            outChars = outChars + chars[(int)index];
            // 每次循环按位右移 5 位
            lHexLong >>= 5;
        }

        return outChars;
    }


}