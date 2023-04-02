package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder;
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
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoSearchingIntegrationTest {

    public static final UUID PHOTO_ID = UUID.randomUUID();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    public void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void searchByIdShouldReturnPhoto() {
        Photo photo = aPhoto("title", PHOTO_ID);
        addPhotoToDb(photo);

        ResponseEntity<PhotoDto> result = restTemplate.getForEntity(getGalleryBasePath() + "/" + PHOTO_ID, PhotoDto.class);
        PhotoDto expected = aPhotoDto("title", PHOTO_ID);
        assertThat(reflectionEquals(result.getBody(), expected, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void searchByNameShouldReturnPhoto() {
        String photoTitle = "my photo";
        Photo photo = aPhoto(photoTitle, PHOTO_ID);
        addPhotoToDb(photo);

        ResponseEntity<PhotoDto[]> result = restTemplate.getForEntity(getGalleryBasePath() + "/search?title=" + photoTitle, PhotoDto[].class);
        List<PhotoDto> resultList = Arrays.asList(result.getBody());

        PhotoDto expected = aPhotoDto(photoTitle, PHOTO_ID);
        assertThat(resultList.size(), is(1));
        assertThat(reflectionEquals(resultList.get(0), expected, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void searchAllShouldReturnPhotos() {
        String photo1 = "photo1";
        String photo2 = "photo2";
        String photo3 = "photo3";
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        addPhotoToDb(aPhoto(photo1, photoId1));
        addPhotoToDb(aPhoto(photo2, photoId2));
        addPhotoToDb(aPhoto(photo3, photoId3));
        ResponseEntity<PhotoDto[]> result = restTemplate.exchange(
                getGalleryBasePath(),
                HttpMethod.GET,
                null,
                PhotoDto[].class);

        PhotoDto photoDto1 = aPhotoDto(photo1, photoId1);
        PhotoDto photoDto2 = aPhotoDto(photo2, photoId2);
        PhotoDto photoDto3 = aPhotoDto(photo3, photoId3);

        List<PhotoDto> resultList = Arrays.asList(result.getBody());
        assertThat(resultList.size(), is(3));
        assertThat(reflectionEquals(resultList.get(0), photoDto1, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(reflectionEquals(resultList.get(1), photoDto2, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(reflectionEquals(resultList.get(2), photoDto3, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
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

    private String getGalleryBasePath() {
        return "http://localhost:" + port + "/api/v1/gallery";
    }
}
