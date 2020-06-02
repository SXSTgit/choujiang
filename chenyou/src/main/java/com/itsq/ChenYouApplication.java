package com.itsq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.itsq.mapper")
@EnableAsync//开启异步注解
//@EnableScheduling//开启定时任务
@EnableSwagger2
public class ChenYouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChenYouApplication.class, args);
    }

}
