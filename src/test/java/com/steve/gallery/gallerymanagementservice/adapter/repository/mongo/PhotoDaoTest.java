package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.integration.MongoTestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(classes = MongoTestConfiguration.class)
public class PhotoDaoTest {

    @Autowired
    MongoTemplate testMongoTemplate;

    @AfterEach
    void tearDown() {
        testMongoTemplate.getDb().drop();
    }

    @Test
    public void retrievesAllPhotosFromPhotosCollection() {
        Photo photo = new Photo();
        testMongoTemplate.save(photo, PHOTO_COLLECTION);

        PhotoDao underTest = new PhotoDao(testMongoTemplate);


        List<Photo> result = underTest.findAllPhotos();

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

}