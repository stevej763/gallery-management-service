package com.steve.gallery.gallerymanagementservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfigurationContext {

    private final String database;
    private final String host;
    private final String port;

    public MongoConfigurationContext(String database, String host, String port) {
        this.database = database;
        this.host = host;
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
