package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoSearchingIntegrationTest extends BaseWebIntegrationTest {

    public static final UUID PHOTO_ID = UUID.randomUUID();
    public static final String PHOTO_TITLE = "title";

    @Test
    public void searchByIdShouldReturnPhoto() {
        PhotoMetadata photo = aPhoto(PHOTO_TITLE, PHOTO_ID);
        savePhotoToDatabase(photo);

        ResponseEntity<PhotoDto> result = restTemplate.getForEntity(getGalleryBasePath() + "/" + PHOTO_ID, PhotoDto.class);
        PhotoDto expected = aPhotoDto(PHOTO_TITLE, PHOTO_ID);
        assertThat(reflectionEquals(result.getBody(), expected, "createdAt", "modifiedAt", "originalImageUrl"), is(true));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void searchByNameShouldReturnPhoto() {
        String photoTitle = "my photo";
        PhotoMetadata photo = aPhoto(photoTitle, PHOTO_ID);
        savePhotoToDatabase(photo);

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
        savePhotoToDatabase(aPhoto(photo1, photoId1));
        savePhotoToDatabase(aPhoto(photo2, photoId2));
        savePhotoToDatabase(aPhoto(photo3, photoId3));
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

    private PhotoDto aPhotoDto(String photoTitle, UUID photoId) {
        return new PhotoDtoBuilder()
                .withTitle(photoTitle)
                .withPhotoId(photoId)
                .build();
    }

    private PhotoMetadata aPhoto(String photoTitle, UUID photoId) {
        return new PhotoMetadataBuilder()
                .withPhotoId(photoId)
                .withTitle(photoTitle)
                .build();
    }
}
