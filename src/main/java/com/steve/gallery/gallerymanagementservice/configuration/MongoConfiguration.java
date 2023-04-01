package com.steve.gallery.gallerymanagementservice.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.mongodb.MongoCredential.createCredential;

@ConfigurationPropertiesScan("com.steve.gallery.gallerymanagementservice.configuration")
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
    MongoClientSettings.Builder clientSettingsBuilder =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .uuidRepresentation(UuidRepresentation.STANDARD);
    if (mongoConfigurationContext.isRequiresAuth()) {
      MongoCredential credential =
          createCredential(
              mongoConfigurationContext.getUsername(),
              mongoConfigurationContext.getAuthenticationDatabase(),
              mongoConfigurationContext.getPassword().toCharArray());
      clientSettingsBuilder.credential(credential);
    }
    return MongoClients.create(clientSettingsBuilder.build());
  }

  private ConnectionString createConnectionString() {
    String connectionString =
        String.format(
            "mongodb://%s:%s",
            mongoConfigurationContext.getHost(), mongoConfigurationContext.getPort());
    return new ConnectionString(connectionString);
  }
}
