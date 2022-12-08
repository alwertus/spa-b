package com.tretsoft.spa.config;

import com.tretsoft.spa.config.props.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfiguration {

    final AppProperties appProperties;

    public AppConfiguration(AppProperties properties) {
        this.appProperties = properties;
    }

}