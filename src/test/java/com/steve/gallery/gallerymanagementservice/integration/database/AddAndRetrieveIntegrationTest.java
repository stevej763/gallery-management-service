package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class AddAndRetrieveIntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void canCreateAPhotoThenRetrieveItFromTheDatabase() {
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao);
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        PhotoCreationService photoCreationService = new PhotoCreationService(photoRepository, new PhotoFactory(), new PhotoDtoFactory("baseUrl"));
        String title = "title";
        String description = "description";
        List<String> tags = List.of(UUID.randomUUID().toString());
        List<String> categories = List.of(UUID.randomUUID().toString());
        photoCreationService.create(new PhotoUploadRequest(title, description, tags, categories));

        List<Photo> results = photoFinder.findAll();

        assertThat(results.size(), is(1));
        Photo result = results.get(0);
        assertThat(result.getTitle(), is(title));
        assertThat(result.getDescription(), is(description));
        assertThat(result.getTags(), is(tags));
        assertThat(result.getCategories(), is(categories));
    }
}
