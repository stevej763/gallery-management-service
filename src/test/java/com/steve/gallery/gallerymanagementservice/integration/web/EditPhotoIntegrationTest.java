package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TitleEditRequestDto;
import com.steve.gallery.gallerymanagementservice.domain.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EditPhotoIntegrationTest extends BaseWebIntegrationTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final String TITLE = "title";
    private static final Photo PHOTO = new PhotoBuilder()
            .withTitle(TITLE)
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    private static final PhotoMetadata PHOTO_METADATA = new PhotoMetadataBuilder()
            .withTitle(TITLE)
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Test
    public void canEditTitle() {
        String myNewTitle = "my new title";
        savePhotoToDatabase(PHOTO_METADATA);


        String url = getAdminBasePath() + "/edit/" + PHOTO_ID + "/title";
        TitleEditRequestDto request = new TitleEditRequestDto(PHOTO_ID, myNewTitle);

        ResponseEntity<PhotoDto> response = restTemplate.postForEntity(url, request, PhotoDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getTitle(), is(myNewTitle));
        assertThat(response.getBody().getPhotoId(), is(PHOTO_ID));
    }

    @Test
    public void canEditDescription() {
        String descriptionChange = "change";
        savePhotoToDatabase(PHOTO_METADATA);


        String url = getAdminBasePath() + "/edit/" + PHOTO_ID + "/description";
        DescriptionEditRequest request = new DescriptionEditRequest(PHOTO_ID, descriptionChange);

        ResponseEntity<PhotoDto> response = restTemplate.postForEntity(url, request, PhotoDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getDescription(), is(descriptionChange));
        assertThat(response.getBody().getPhotoId(), is(PHOTO_ID));
    }
}
