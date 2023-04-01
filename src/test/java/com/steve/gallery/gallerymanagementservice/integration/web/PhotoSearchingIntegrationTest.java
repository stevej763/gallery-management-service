package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.rest.client.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.PhotoDtoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoSearchingIntegrationTest {

    public static final UUID PHOTO_ID = UUID.randomUUID();
    public static final String PHOTO_TITLE = "title";
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
        Photo photo = aPhoto("title", PHOTO_ID);
        addPhotoToDb(photo);

        ResponseEntity<PhotoDto> result = restTemplate.getForEntity(getGalleryBasePath() + "/" + PHOTO_ID, PhotoDto.class);
        assertThat(result.getBody(), is(aPhotoDto("title", PHOTO_ID)));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void searchByNameShouldReturnPhoto() {
        String photoTitle = "my photo";
        Photo photo = aPhoto(photoTitle, PHOTO_ID);
        addPhotoToDb(photo);

        ResponseEntity<PhotoDto[]> result = restTemplate.getForEntity(getGalleryBasePath() + "/search?title=" + photoTitle, PhotoDto[].class);
        List<PhotoDto> resultList = Arrays.asList(result.getBody());

        List<PhotoDto> expected = List.of(aPhotoDto(photoTitle, PHOTO_ID));
        assertThat(resultList, is(expected));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void searchAllShouldReturnPhotos() {
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        addPhotoToDb(aPhoto(PHOTO_TITLE, photoId1));
        addPhotoToDb(aPhoto(PHOTO_TITLE, photoId2));
        addPhotoToDb(aPhoto(PHOTO_TITLE, photoId3));
        ResponseEntity<PhotoDto[]> result = restTemplate.exchange(
                getGalleryBasePath(),
                HttpMethod.GET,
                null,
                PhotoDto[].class);

        List<PhotoDto> expected = List.of(
                aPhotoDto("title", photoId1),
                aPhotoDto("title", photoId2),
                aPhotoDto("title", photoId3));

        List<PhotoDto> resultList = Arrays.asList(result.getBody());
        assertThat(resultList, is(expected));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    private String getGalleryBasePath() {
        return "http://localhost:" + port + "/api/v1/gallery";
    }

    private void addPhotoToDb(final Photo photo) {
        mongoTemplate.save(photo, PHOTO_COLLECTION);
    }

    private PhotoDto aPhotoDto(String photoTitle, UUID photoId) {
        return new PhotoDtoBuilder()
                .withTitle(photoTitle)
                .withPhotoId(photoId)
                .build();
    }

    private Photo aPhoto(String photoTitle, UUID photoId) {
        return new PhotoBuilder()
                .withPhotoId(photoId)
                .withTitle(photoTitle)
                .build();
    }
}
