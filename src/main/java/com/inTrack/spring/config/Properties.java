package com.inTrack.spring.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author vijayan
 */
@Configuration
@ConfigurationProperties
@Getter
@Setter
public class Properties {

    private String jwtSecret;
    private Long jwtTokenValidity;
}
