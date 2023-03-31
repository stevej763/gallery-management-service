package com.steve.gallery.gallerymanagementservice.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@ConfigurationPropertiesScan
@Configuration
public class MongoConfiguration {

    private final MongoConfigurationContext mongoConfigurationContext;

    public MongoConfiguration(MongoConfigurationContext mongoConfigurationContext) {
        this.mongoConfigurationContext = mongoConfigurationContext;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = createMongoClient();
        return new MongoTemplate(mongoClient, mongoConfigurationContext.getDatabase());
    }

    private MongoClient createMongoClient() {
        ConnectionString connectionString = createConnectionString();
        return MongoClients.create(connectionString);
    }

    private ConnectionString createConnectionString() {
        String connectionString = String.format("mongodb://%s:%s", mongoConfigurationContext.getHost(), mongoConfigurationContext.getPort());
        return new ConnectionString(connectionString);
    }
}
