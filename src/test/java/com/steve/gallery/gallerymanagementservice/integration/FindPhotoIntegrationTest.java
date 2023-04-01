package com.steve.gallery.gallerymanagementservice.integration;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = MongoTestConfiguration.class)
public class FindPhotoIntegrationTest {

    @Autowired
    MongoTemplate testMongoTemplate;

    private static final Photo PHOTO = new PhotoBuilder().build();

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
        assertThat(result, Is.is(photos));
    }

    private void addPhotosToDb() {
        testMongoTemplate.save(PHOTO, "photos");
    }
}
