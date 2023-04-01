package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.GalleryManagementService;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = GalleryManagementService.class)
public class FindPhotoWebIntegrationTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void searchByIdShouldReturnPhoto() {
        UUID photoId = UUID.randomUUID();
        addPhotosToDb(photoId);

        ResponseEntity<PhotoDto> result = restTemplate.getForEntity("http://localhost:" + port + "/gallery/" + photoId, PhotoDto.class);
        assertThat(result.getBody(), is(new PhotoDto(photoId)));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    private void addPhotosToDb(UUID id) {
        mongoTemplate.save(new Photo(id), PHOTO_COLLECTION);
    }
}
