package com.justdo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * war包部署需要
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan({"com.justdo.*.*.dao" , "com.justdo.*.dao"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JustdoApplication2 extends SpringBootServletInitializer{

    private static final Logger logger = LoggerFactory.getLogger(JustdoApplication2.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    public static void main(String[] args) {
        SpringApplication.run(JustdoApplication2.class, args);
    }
}