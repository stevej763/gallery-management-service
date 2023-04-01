package com.steve.gallery.gallerymanagementservice.configuration.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan("com.steve.gallery.gallerymanagementservice.configuration.aws")
@Configuration
public class S3Configuration {

    private final S3ConfigurationContext s3ConfigurationContext;
    private final AwsAuthenticationContext awsAuthenticationContext;

    public S3Configuration(S3ConfigurationContext s3ConfigurationContext, AwsAuthenticationContext awsAuthenticationContext) {
        this.s3ConfigurationContext = s3ConfigurationContext;
        this.awsAuthenticationContext = awsAuthenticationContext;
    }

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAuthenticationContext.getAccessKey(), awsAuthenticationContext.getSecretKey());
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(s3ConfigurationContext.getEndpoint(), s3ConfigurationContext.getRegion());
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
