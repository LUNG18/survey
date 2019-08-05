package com.gobestsoft.survey;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.gobestsoft.survey.mapper")
@Slf4j
public class SurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
        log.info("-----启动完成-----");
    }

}
