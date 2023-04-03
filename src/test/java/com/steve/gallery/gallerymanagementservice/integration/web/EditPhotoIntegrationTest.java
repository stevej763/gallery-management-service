package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TagRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TitleEditRequestDto;
import com.steve.gallery.gallerymanagementservice.domain.DescriptionEditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EditPhotoIntegrationTest extends BaseWebIntegrationTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final String TITLE = "title";
    private static final String ORIGINAL_TAG = "original tag";

    private static final PhotoMetadata PHOTO_METADATA = new PhotoMetadataBuilder()
            .withTitle(TITLE)
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withTags(List.of(ORIGINAL_TAG))
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Test
    public void canEditTitle() {
        String myNewTitle = "my new title";
        savePhotoToDatabase(PHOTO_METADATA);


        String url = getAdminBasePath() + "/edit/" + PHOTO_ID + "/title";
        TitleEditRequestDto request = new TitleEditRequestDto(myNewTitle);

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

    @Test
    public void canAddTag() {
        String appendedTag = "appended tag";
        savePhotoToDatabase(PHOTO_METADATA);

        String url = getAdminBasePath() + "/edit/" + PHOTO_ID + "/tag/add";
        TagRequestDto request = new TagRequestDto(appendedTag);

        ResponseEntity<PhotoDto> response = restTemplate.postForEntity(url, request, PhotoDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getTags(), is(List.of(ORIGINAL_TAG, appendedTag)));
        assertThat(response.getBody().getPhotoId(), is(PHOTO_ID));
    }

    @Test
    public void canRemoveTags() {
        savePhotoToDatabase(PHOTO_METADATA);

        String url = getAdminBasePath() + "/edit/" + PHOTO_ID + "/tag/delete";
        TagRequestDto request = new TagRequestDto(ORIGINAL_TAG);

        ResponseEntity<PhotoDto> response = restTemplate.postForEntity(url, request, PhotoDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getTags(), is(emptyList()));
        assertThat(response.getBody().getPhotoId(), is(PHOTO_ID));
    }
}
