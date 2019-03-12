package com.justdo.common.utils;


import com.justdo.config.Constant;
import com.justdo.common.domain.ColumnDO;
import com.justdo.common.domain.TableDO;
import com.justdo.common.exception.BDException;
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
public class GenUtils {


    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
       // templates.add("templates/system/generator/domain.java.vm");
//        templates.add("templates/system/generator/Dao.java.vm");
//        //templates.add("templates/generator/Mapper.java.vm");
//        templates.add("templates/system/generator/Mapper.xml.vm");
//        templates.add("templates/system/generator/Service.java.vm");
//        templates.add("templates/system/generator/ServiceImpl.java.vm");
//        templates.add("templates/system/generator/Controller.java.vm");
//        templates.add("templates/system/generator/list.html.vm");
//        templates.add("templates/system/generator/add.html.vm");
//        templates.add("templates/system/generator/edit.html.vm");
//        templates.add("templates/system/generator/list.js.vm");
//        templates.add("templates/system/generator/add.js.vm");
//        templates.add("templates/system/generator/edit.js.vm");
        //templates.add("templates/generator/menu.sql.vm");

        templates.add("templates/system/generator/code-template1/domain/domain.java.vm");
        templates.add("templates/system/generator/code-template1/dao/DAO.java.vm");
        templates.add("templates/system/generator/code-template1/dao/hibernateImpl/DAOImpl.java.vm");
        templates.add("templates/system/generator/code-template1/service/Service.java.vm");
        templates.add("templates/system/generator/code-template1/service/ServiceImpl.java.vm");
        templates.add("templates/system/generator/code-template1/action/SaveAction.java.vm");
        templates.add("templates/system/generator/code-template1/action/ShowAction.java.vm");
        templates.add("templates/system/generator/code-template1/page/form.jsp.vm");
        templates.add("templates/system/generator/code-template1/page/list.jsp.vm");
        templates.add("templates/system/generator/code-template1/page/view.jsp.vm");

        return templates;
    }

    /**
     * 生成代码
     */


    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"), config.getString("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));
        String constructorParams = "";
        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
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
            constructorParams += attrType+" "+attrName+",";
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }
            else
            {
                columsList.add(columnDO);
            }

        }
        if(constructorParams.endsWith(",")){
            constructorParams = constructorParams.substring(0,constructorParams.length()-2);
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
                zip.putNextEntry(new ZipEntry(getFileName2(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1))));
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
        if (Constant.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
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
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + classname +File.separator+ "domain" + File.separator + className + ".java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath +classname +File.separator+ "dao" + File.separator + className + "DAO.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + className + "Service.java";
        }



        if (template.contains("SaveAction.java.vm")) {
            return packagePath +classname +File.separator+ "action" + File.separator + className + "SaveAction.java";
        }

        if (template.contains("ShowAction.java.vm")) {
            return packagePath +classname +File.separator+ "action" + File.separator + className + "ShowAction.java";
        }


        if (template.contains("list.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  + className.toLowerCase() + File.separator +  className.toLowerCase()+"list.jsp";
        }
        if (template.contains("form.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  +  className.toLowerCase() + File.separator +  className.toLowerCase()+"form.jsp";
        }
        if (template.contains("view.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  +  className.toLowerCase() + File.separator +  className.toLowerCase()+"view.jsp";
        }

//		if(template.contains("menu.sql.vm")){
//			return className.toLowerCase() + "_menu.sql";
//		}

        return null;
    }

    /**
     * 获取文件名2
     */
    public static String getFileName2(String template, String classname, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + classname +File.separator+ "domain" + File.separator + className + ".java";
        }

        if (template.contains("DAO.java.vm")) {
            return packagePath +classname +File.separator+ "dao" + File.separator + className + "DAO.java";
        }

        if (template.contains("DAOImpl.java.vm")) {
            return packagePath +classname +File.separator+ "dao" + File.separator+"hibernateImpl"+File.separator + className + "DAOImpl.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath +classname +File.separator+ "service" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("SaveAction.java.vm")) {
            return packagePath +classname +File.separator+ "action" + File.separator + className + "SaveAction.java";
        }

        if (template.contains("ShowAction.java.vm")) {
            return packagePath +classname +File.separator+ "action" + File.separator + className + "ShowAction.java";
        }

        if (template.contains("list.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  + className.toLowerCase() + File.separator +  className.toLowerCase()+"list.jsp";
        }
        if (template.contains("form.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  +  className.toLowerCase() + File.separator +  className.toLowerCase()+"form.jsp";
        }
        if (template.contains("view.jsp.vm")) {
            return "main" + File.separator + "pages" + File.separator  +  className.toLowerCase() + File.separator +  className.toLowerCase()+"view.jsp";
        }

        return null;
    }
}
