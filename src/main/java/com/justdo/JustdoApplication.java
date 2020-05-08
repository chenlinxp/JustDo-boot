package com.justdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan({"com.justdo.*.*.dao" , "com.justdo.*.dao"})
@SpringBootApplication
//@EnableAutoConfiguration
public class JustdoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JustdoApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ   justdo启动成功      ヾ(◍°∇°◍)ﾉﾞ");
    }

}
