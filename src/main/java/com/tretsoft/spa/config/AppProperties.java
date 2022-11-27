package com.tretsoft.spa.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProperties {

    private String frontUrl;
    private Boolean mockEmailSending = false;
}
