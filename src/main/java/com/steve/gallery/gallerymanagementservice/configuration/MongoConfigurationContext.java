package com.steve.gallery.gallerymanagementservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfigurationContext {

    private final String database;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String authDatabase;

    public MongoConfigurationContext(String database, String host, String port, String username, String password, String authDatabase) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.authDatabase = authDatabase;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthDatabase() {
        return authDatabase;
    }
}
