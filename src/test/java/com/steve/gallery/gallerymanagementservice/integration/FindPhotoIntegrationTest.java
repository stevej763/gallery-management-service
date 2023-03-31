package com.steve.gallery.gallerymanagementservice.integration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.configuration.MongoConfigurationContext;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = MongoTestConfiguration.class)
public class FindPhotoIntegrationTest {

    @Autowired
    MongoTemplate testMongoTemplate;

    private static final Photo PHOTO = new Photo();

    @AfterEach
    void tearDown() {
        testMongoTemplate.getDb().drop();
    }

    @Test
    public void canReturnAListOfPhotos() {
        addPhotosToDb();
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(testMongoTemplate);
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        List<Photo> result = photoFinder.findAll();

        List<Photo> photos = List.of(PHOTO);
        assertThat(result, Is.is(photos));
    }

    private void addPhotosToDb() {
        testMongoTemplate.save(PHOTO, "photos");
    }
}
