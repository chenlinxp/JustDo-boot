package com.justdo.common.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.justdo.common.domain.ColumnDO;
import com.justdo.common.domain.TableDO;
import com.justdo.common.exception.BDException;
import com.justdo.config.ConstantConfig;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GeneratorCodeUtils {


    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/system/generator/template/domain.java.vm");
        templates.add("templates/system/generator/template/Dao.java.vm");
        templates.add("templates/system/generator/template/Mapper.java.vm");
        templates.add("templates/system/generator/template/Mapper.xml.vm");
        templates.add("templates/system/generator/template/Service.java.vm");
        templates.add("templates/system/generator/template/ServiceImpl.java.vm");
        templates.add("templates/system/generator/template/Controller.java.vm");
        templates.add("templates/system/generator/template/list.html.vm");
        templates.add("templates/system/generator/template/add.html.vm");
        templates.add("templates/system/generator/template/edit.html.vm");
        templates.add("templates/system/generator/template/view.html.vm");
        templates.add("templates/system/generator/template/list.js.vm");
        templates.add("templates/system/generator/template/add.js.vm");
        templates.add("templates/system/generator/template/edit.js.vm");
        templates.add("templates/system/generator/template/view.js.vm");
        templates.add("templates/system/generator/template/resource.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */

    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip, JSONArray tabledatajson) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        String tableComment = "空白注释";
        if(table.get("tableComment")!=null) {
            tableComment = table.get("tableComment").toString();
        }
        tableDO.setComments(tableComment);
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"), config.getString("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));
        tableDO.setClassnametolower(className.toLowerCase());
        String constructorParams = "";
        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            if(tabledatajson.size()!=0){
                for(Object json :  tabledatajson){
                    JSONObject obj = (JSONObject)json;
                    if(obj.containsValue(column.get("columnName").toString())){
                        columnDO.setDisplayType(obj.getString("displayType"));
                        columnDO.setSearchType(obj.getString("searchType"));
                        columnDO.setOrderNum(Integer.parseInt(obj.getString("orderNum")));
                        columnDO.setComments(obj.getString("columnComment"));
                        columnDO.setDictCode(obj.getString("columnComment"));
                    }
                }
            }
            String columnName = column.get("columnName").toString();
            String columnnametolower = columnName.toLowerCase();
            columnDO.setColumnName(columnName);
            columnDO.setColumnnametolower(columnnametolower);
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));//'auto_increment'

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));
            columnDO.setAttrnametolower(attrName.toLowerCase());
            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);
            constructorParams += attrType+" "+columnDO.getAttrname()+",";
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }
            else
            {
                columsList.add(columnDO);
            }
            //根据orderNum进行asc排序
            Collections.sort(columsList, new Comparator<ColumnDO>() {
                @Override
                public int compare(ColumnDO o1, ColumnDO o2) {
                    int i = o1.getOrderNum()- o2.getOrderNum();
                    return i;
                }
            });
        }
        if(constructorParams.endsWith(",")){
            constructorParams = constructorParams.substring(0,constructorParams.length()-1);
        }
        tableDO.setConstructorParams(constructorParams);


        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(columsList.get(0));
            columsList.remove(0);
        }
        tableDO.setColumns(columsList);
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());

        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("pk-tolower", tableDO.getPk().getAttrname().toLowerCase());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("classnametolower", tableDO.getClassnametolower());
        map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("constructorParams", tableDO.getConstructorParams());
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            try {
                //渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new BDException("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (ConstantConfig.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        classname = classname.toLowerCase();
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + classname +File.separator+ "domain" + File.separator + className + "DO.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath +classname +File.separator+ "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Mapper.java.vm")){
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath +classname +File.separator+ "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName + File.separator + className + "Mapper.xml";
        }
        if(template.contains("resource.sql.vm")){
            return "main" + File.separator + "resources" + File.separator + "sql" + File.separator + packageName + File.separator + className + "resource.sql";
        }
        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + className.toLowerCase() + ".html";
        }
        if (template.contains("add.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + "add.html";
        }
        if (template.contains("edit.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + "edit.html";
        }
        if (template.contains("view.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + "view.html";
        }
        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + className.toLowerCase() + ".js";
        }
        if (template.contains("add.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + "add.js";
        }
        if (template.contains("edit.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + "edit.js";
        }
        if (template.contains("view.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + "view.js";
        }
        return null;
    }
}
