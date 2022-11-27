package com.tretsoft.spa;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Log4j2
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpaApplication.class, args);
        log.info("===== ===== SPA-B Application is started ===== ====");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpaApplication.class);
    }
}
