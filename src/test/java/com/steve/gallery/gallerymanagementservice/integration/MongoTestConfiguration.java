package com.steve.gallery.gallerymanagementservice.integration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.steve.gallery.gallerymanagementservice.configuration.MongoConfigurationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;
import java.util.UUID;

import static com.mongodb.MongoCredential.*;
import static java.lang.Boolean.*;

@ConfigurationPropertiesScan("com.steve.gallery.gallerymanagementservice.configuration")
@Configuration
public class MongoTestConfiguration {

    private final String databaseName;

    private final MongoConfigurationContext mongoConfigurationContext;
    private final Environment environment;

    public MongoTestConfiguration(MongoConfigurationContext mongoConfigurationContext, Environment environment) {
        this.mongoConfigurationContext = mongoConfigurationContext;
        this.environment = environment;
        this.databaseName = UUID.randomUUID().toString();
    }

    @Bean
    public MongoTemplate testMongoTemplate() {
        MongoClient mongoClient = createMongoClient();
        return new MongoTemplate(mongoClient, databaseName);
    }

    private MongoClient createMongoClient() {
        ConnectionString connectionString = createConnectionString();
        MongoClientSettings.Builder clientSettingsBuilder = MongoClientSettings.builder()
                .applyConnectionString(connectionString);
        if (mongoRequiresAuthentication()) {
            MongoCredential credential = createCredential(
                    mongoConfigurationContext.getUsername(),
                    mongoConfigurationContext.getAuthDatabase(),
                    mongoConfigurationContext.getPassword().toCharArray());
            clientSettingsBuilder.credential(credential);
        }
        return MongoClients.create(connectionString);
    }

    private boolean mongoRequiresAuthentication() {
        return parseBoolean(environment.getProperty("spring.data.mongodb.requiresAuth"));
    }

    private ConnectionString createConnectionString() {
        String connectionString = String.format("mongodb://%s:%s", mongoConfigurationContext.getHost(), mongoConfigurationContext.getPort());
        return new ConnectionString(connectionString);
    }
}
