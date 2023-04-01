package com.steve.gallery.gallerymanagementservice.configuration.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3ConfigurationContext {

    private final String endpoint;
    private final String bucketName;
    private final String region;

    public S3ConfigurationContext(String endpoint, String bucketName, String region) {
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.region = region;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }
}
