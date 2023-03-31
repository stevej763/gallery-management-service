package com.steve.gallery.gallerymanagementservice.integration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.configuration.MongoConfigurationContext;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.MongoCredentialPropertyEditor;
import org.springframework.data.mongodb.core.MongoClientSettingsFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@DataMongoTest(excludeAutoConfiguration = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class FindPhotoIntegrationTest {

    @Autowired
    Environment environment;

    private MongoTemplate mongoTemplate;

    private static final String DATABASE_NAME = UUID.randomUUID().toString();
    private static final Photo PHOTO = new Photo();

    @BeforeEach
    void setUp() {
        String host = environment.getProperty("spring.data.mongodb.host");
        String port = environment.getProperty("spring.data.mongodb.port");
        String connectionString = String.format("mongodb://%s:%s", host, port);
        String mongoUser = "steve";
        String mongoAdminDb = "admin";
        char[] mongoPassword = "password".toCharArray();
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .credential(MongoCredential.createCredential(mongoUser, mongoAdminDb, mongoPassword))
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        mongoTemplate = new MongoTemplate(MongoClients.create(mongoClientSettings), DATABASE_NAME);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void canReturnAListOfPhotos() {
        addPhotosToDb();

        MongoPhotoRepository photoRepository = new MongoPhotoRepository(mongoTemplate);
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        List<Photo> result = photoFinder.findAll();

        List<Photo> photos = List.of(PHOTO);
        assertThat(result, Is.is(photos));
    }

    private void addPhotosToDb() {
        mongoTemplate.save(PHOTO, "photos");
    }
}
