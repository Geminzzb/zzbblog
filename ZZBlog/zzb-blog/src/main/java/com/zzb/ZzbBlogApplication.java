package com.zzb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 13:28
 * @mail: 2218722664@qq.com
 * @info:
 */
@SpringBootApplication
@MapperScan("com.zzb.mapper")
@EnableScheduling
@EnableSwagger2
public class ZzbBlogApplication {
//    @EnableScheduling
//    @EnableSwagger2
    public static void main(String[] args) {
        SpringApplication.run(ZzbBlogApplication.class,args);
    }
}
