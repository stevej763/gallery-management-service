package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class FindPhotoMongoIntegrationTest {

    @Autowired
    MongoTemplate testMongoTemplate;

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final Photo PHOTO = new PhotoBuilder()
            .withPhotoId(PHOTO_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @AfterEach
    void tearDown() {
        testMongoTemplate.getDb().drop();
    }

    @Test
    public void canReturnAListOfPhotos() {
        addPhotosToDb();
        PhotoDao photoDao = new PhotoDao(testMongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao);
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        List<Photo> result = photoFinder.findAll();

        List<Photo> photos = List.of(PHOTO);
        assertThat(result, is(photos));
    }

    @Test
    public void canReturnAPhotoById() {
        addPhotosToDb();
        PhotoDao photoDao = new PhotoDao(testMongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao);
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        Photo result = photoFinder.findPhotoById(PHOTO_ID);

        assertThat(result, is(PHOTO));
    }

    private void addPhotosToDb() {
        testMongoTemplate.save(PHOTO, "photos");
    }
}
