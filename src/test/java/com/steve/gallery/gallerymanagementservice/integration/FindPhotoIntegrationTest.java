package com.steve.gallery.gallerymanagementservice.integration;

import com.mongodb.client.MongoClients;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@DataMongoTest
public class FindPhotoIntegrationTest {

    private static final String DATABASE_NAME = UUID.randomUUID().toString();
    private static final Photo PHOTO = new Photo();
    private final MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(), DATABASE_NAME);

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
