package com.vie.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.mybatis.spring.annotation.MapperScan;

/**
 * VIE Application Starter
 */
@SpringBootApplication(
        excludeName = {
                "org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration"
        })
@ComponentScan(basePackages = {"com.vie"})
@MapperScan(basePackages = {"com.vie.db.mapper"})
@EnableScheduling
public class VieApplication {

    public static void main(String[] args) {
        SpringApplication.run(VieApplication.class, args);
    }
}
