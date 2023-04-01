package com.steve.gallery.gallerymanagementservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3")
public class S3ConfigurationContext {

    private final String baseUrl;

    public S3ConfigurationContext(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
