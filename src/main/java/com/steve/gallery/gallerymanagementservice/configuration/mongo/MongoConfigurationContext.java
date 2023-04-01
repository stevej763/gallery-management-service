package com.steve.gallery.gallerymanagementservice.configuration.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoConfigurationContext {

    private final String database;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String authenticationDatabase;
    private final boolean requiresAuth;
    private final String applicationName;

    public MongoConfigurationContext(
            String applicationName,
            String database,
            String host,
            String port,
            String username,
            String password,
            String authenticationDatabase,
            boolean requiresAuth) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.authenticationDatabase = authenticationDatabase;
        this.requiresAuth = requiresAuth;
        this.applicationName = applicationName;
    }

    public String getDatabase() {
        return database;
    }

    public String getApplicationName() {
        return applicationName;
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

    public String getAuthenticationDatabase() {
        return authenticationDatabase;
    }

    public boolean isRequiresAuth() {
        return requiresAuth;
    }
}
