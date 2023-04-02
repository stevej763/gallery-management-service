package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoDeletionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpMethod.DELETE;

public class PhotoDeletionIntegrationTest extends BaseWebIntegrationTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final PhotoMetadata PHOTO_METADATA = new PhotoMetadataBuilder()
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Test
    public void canDeleteAPhoto() throws IOException {
        prepareTestPhoto();

        String deletionUrl = getAdminBasePath() + "/delete/" + PHOTO_ID;
        ResponseEntity<PhotoDeletionResponseDto> response = restTemplate.exchange(deletionUrl, DELETE, null, PhotoDeletionResponseDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new PhotoDeletionResponseDto(true)));
    }

    private void prepareTestPhoto() throws IOException {
        savePhotoToDatabase(PHOTO_METADATA);
        createFileInBucket(UPLOAD_ID);
    }
}
