package com.challenge.dnaanalyzerservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class MongoConfig {

    @Value("${mongodb.uri}")
    private String uri;

    @Value("${mongodb.name}")
    private String name;

}

