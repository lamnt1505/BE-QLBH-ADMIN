package com.vnpt.mini_project_java;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class MiniProjectJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniProjectJavaApplication.class, args);
    }
}
