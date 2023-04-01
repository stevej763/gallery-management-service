package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

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

    @Test
    public void shouldHandlePhotoUploadRequest() {
        PhotoCreationService photoCreationService = mock(PhotoCreationService.class);
        PhotoManagementResource photoManagementResource = new PhotoManagementResource(photoCreationService, new PhotoUploadRequestFactory());

        PhotoUploadMetadataDto photoUploadMetadataDto = aPhotoUploadMetadataDtoBuilder()
                .withTitle("title")
                .withDescription("description")
                .withTags(List.of("tag"))
                .withCategories(List.of("categories"))
                .build();

        PhotoDto photoDto = aPhotoDto()
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

        when(photoCreationService.create(new PhotoUploadRequest(photoUploadMetadataDto.getTitle(), photoUploadMetadataDto.getDescription(), photoUploadMetadataDto.getTags(), photoUploadMetadataDto.getCategories()))).thenReturn(photoDto);

        MockMultipartFile multipartFile = new MockMultipartFile("name", "test".getBytes());
        ResponseEntity<PhotoDto> result = photoManagementResource.upload(multipartFile, photoUploadMetadataDto);

        assertThat(result, is(ResponseEntity.ok(photoDto)));
    }

}