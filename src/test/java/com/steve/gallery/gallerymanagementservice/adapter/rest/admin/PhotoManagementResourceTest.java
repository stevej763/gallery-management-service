package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource.PhotoManagementResource;
import com.steve.gallery.gallerymanagementservice.domain.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDeletionService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoManagementResourceTest {

    private static final MockMultipartFile UPLOADED_FILE = new MockMultipartFile("name", "test".getBytes());
    private final PhotoUploadRequestFactory photoUploadRequestFactory = mock(PhotoUploadRequestFactory.class);
    private final PhotoCreationService photoCreationService = mock(PhotoCreationService.class);
    private final PhotoDeletionService photoDeletionService = mock(PhotoDeletionService.class);
    private final PhotoManagementResource underTest = new PhotoManagementResource(photoCreationService, photoUploadRequestFactory, photoDeletionService);

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
    public void shouldHandlePhotoDeletionRequest() {
        UUID photoId = UUID.randomUUID();

        PhotoDeletionResponse photoDeletionResponse = new PhotoDeletionResponse(photoId, true, true);
        when(photoDeletionService.deletePhoto(photoId)).thenReturn(photoDeletionResponse);

        ResponseEntity<PhotoDeletionResponseDto> result = underTest.delete(photoId);

        assertThat(result, is(ResponseEntity.ok(new PhotoDeletionResponseDto(true))));
    }

    @Test
    public void shouldReturnFalseIfRecordDeletionFailed() {
        UUID photoId = UUID.randomUUID();

        PhotoDeletionResponse photoDeletionResponse = new PhotoDeletionResponse(photoId, false, true);
        when(photoDeletionService.deletePhoto(photoId)).thenReturn(photoDeletionResponse);

        ResponseEntity<PhotoDeletionResponseDto> result = underTest.delete(photoId);

        assertThat(result, is(ResponseEntity.ok(new PhotoDeletionResponseDto(false, "record deletion failed"))));
    }

    @Test
    public void shouldReturnFalseIfFileDeletionFailed() {
        UUID photoId = UUID.randomUUID();

        PhotoDeletionResponse photoDeletionResponse = new PhotoDeletionResponse(photoId, true, false);
        when(photoDeletionService.deletePhoto(photoId)).thenReturn(photoDeletionResponse);

        ResponseEntity<PhotoDeletionResponseDto> result = underTest.delete(photoId);

        assertThat(result, is(ResponseEntity.ok(new PhotoDeletionResponseDto(false, "file deletion failed"))));
    }

    @Test
    public void shouldReturnFalseIfBothFileAndRecordDeletionFailed() {
        UUID photoId = UUID.randomUUID();

        PhotoDeletionResponse photoDeletionResponse = new PhotoDeletionResponse(photoId, false, false);
        when(photoDeletionService.deletePhoto(photoId)).thenReturn(photoDeletionResponse);

        ResponseEntity<PhotoDeletionResponseDto> result = underTest.delete(photoId);

        assertThat(result, is(ResponseEntity.ok(new PhotoDeletionResponseDto(false, "failed to delete record and file"))));
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