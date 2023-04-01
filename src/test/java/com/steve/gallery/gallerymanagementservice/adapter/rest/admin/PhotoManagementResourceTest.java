package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoUploadMetadataDtoBuilder.aPhotoUploadMetadataDtoBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoManagementResourceTest {

    public static final MockMultipartFile UPLOADED_FILE = new MockMultipartFile("name", "test".getBytes());
    private final PhotoUploadRequestFactory photoUploadRequestFactory = mock(PhotoUploadRequestFactory.class);
    private final PhotoCreationService photoCreationService = mock(PhotoCreationService.class);
    private final PhotoManagementResource underTest = new PhotoManagementResource(photoCreationService, photoUploadRequestFactory);

    @Test
    public void shouldHandlePhotoUploadRequest() throws IOException {
        PhotoUploadMetadataDto photoUploadMetadataDto = createPhotoUploadMetadataDto();

        PhotoDto photoDto = createPhotoDto();

        PhotoUploadRequest photoUploadRequest = createPhotoUploadRequest(photoUploadMetadataDto);
        when(photoCreationService.create(photoUploadRequest)).thenReturn(photoDto);
        when(photoUploadRequestFactory.createPhotoUploadRequest(UPLOADED_FILE, photoUploadMetadataDto)).thenReturn(photoUploadRequest);

        ResponseEntity<PhotoDto> result = underTest.upload(UPLOADED_FILE, photoUploadMetadataDto);

        assertThat(result, is(ResponseEntity.ok(photoDto)));
    }

    @Test
    public void returnsInternalServerErrorWhenFileCausingIoException() throws IOException {
        PhotoUploadMetadataDto photoUploadMetadataDto = createPhotoUploadMetadataDto();

        PhotoDto photoDto = createPhotoDto();

        PhotoUploadRequest photoUploadRequest = createPhotoUploadRequest(photoUploadMetadataDto);
        when(photoCreationService.create(photoUploadRequest)).thenReturn(photoDto);
        when(photoUploadRequestFactory.createPhotoUploadRequest(UPLOADED_FILE, photoUploadMetadataDto)).thenThrow(new IOException("test"));

        ResponseEntity<PhotoDto> result = underTest.upload(UPLOADED_FILE, photoUploadMetadataDto);

        assertThat(result, is(ResponseEntity.internalServerError().build()));
    }

    private PhotoUploadRequest createPhotoUploadRequest(PhotoUploadMetadataDto photoUploadMetadataDto) {
        return new PhotoUploadRequestBuilder()
                .withTitle(photoUploadMetadataDto.getTitle())
                .withDescription(photoUploadMetadataDto.getDescription())
                .withTags(photoUploadMetadataDto.getTags())
                .withCategories(photoUploadMetadataDto.getCategories())
                .build();
    }

    private PhotoDto createPhotoDto() {
        return aPhotoDto()
                .withPhotoId(UUID.randomUUID())
                .withTitle("title")
                .withDescription("description")
                .withTags(List.of("tag"))
                .withCategories(List.of("categories"))
                .withOriginalImageUrl("url")
                .withUploadId(UUID.randomUUID())
                .withModifiedAt(LocalDateTime.now())
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    private PhotoUploadMetadataDto createPhotoUploadMetadataDto() {
        return aPhotoUploadMetadataDtoBuilder()
                .withTitle("title")
                .withDescription("description")
                .withTags(List.of("tag"))
                .withCategories(List.of("categories"))
                .build();
    }

}