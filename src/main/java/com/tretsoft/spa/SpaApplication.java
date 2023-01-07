package com.tretsoft.spa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Log4j2
@SpringBootApplication
public class SpaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        // determine config path
        String configPath = "file:" + System.getProperty("user.home") + "/apps/config/spab.yml";
        System.getProperties().setProperty("spring.config.location", configPath);

        SpringApplication.run(SpaApplication.class, args);
        log.info("Load config file: " + configPath);
        log.info("===== ===== SPA-B Application is started ===== ====");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpaApplication.class);
    }
}
