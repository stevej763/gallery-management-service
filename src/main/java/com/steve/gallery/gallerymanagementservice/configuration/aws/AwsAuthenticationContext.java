package com.steve.gallery.gallerymanagementservice.configuration.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cloud.aws.credentials")
public class AwsAuthenticationContext {

    private final String accessKey;
    private final String secretKey;

    public AwsAuthenticationContext(String accesskey, String secretKey) {
        this.accessKey = accesskey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
