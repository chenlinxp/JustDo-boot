package com.justdo.common.utils;

/**
 * @author justdo
 */

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.Enumeration;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Properties;
        import java.util.UUID;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {
    private final static Map<String, String> regexs = new HashMap<String, String>();

    static{
        Properties entityReferences = new Properties();
        InputStream is = StringUtils.class.getResourceAsStream("regex.properties");
        if (is == null)
            throw new IllegalStateException("Cannot find reference definition file [regex.properties] as class path resource");
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
        if(StringUtils.isBlank(src)) return src;
        return src.replaceAll(reg, with);
    }

    /**
     * 字符串数组转为list集合
     * @param src
     * @return
     */
    public static List<String> toList(String[] src){
        List<String> list = new ArrayList<String>();
        for(String s : src) list.add(s);
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
        for (int i = 0, j = args.length; i < j; i++)
            src = src.replaceAll("\\{" + i + "\\}", args[i]+"");
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
}