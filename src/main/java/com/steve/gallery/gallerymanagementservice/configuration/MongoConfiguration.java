package com.steve.gallery.gallerymanagementservice.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@ConfigurationPropertiesScan
@Configuration
public class MongoConfiguration {

    public static final String DATABASE_NAME = "gallery";

    public MongoConfiguration() {
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://192.168.1.200:27017"));
        return new MongoTemplate(mongoClient, DATABASE_NAME);
    }
}
