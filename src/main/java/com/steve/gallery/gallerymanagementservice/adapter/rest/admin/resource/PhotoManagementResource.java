package com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoDeletionResponseDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoUploadMetadataDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoUploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoCreator;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDeleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoDeletionResponseDto.fileDeletionFailed;
import static com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoDeletionResponseDto.recordDeletionFailed;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/v1/gallery/admin/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class PhotoManagementResource {

    Logger LOGGER = LoggerFactory.getLogger(PhotoManagementResource.class);

    private final PhotoCreator photoCreator;
    private final PhotoUploadRequestFactory photoUploadRequestFactory;
    private final PhotoDeleter photoDeleter;

    public PhotoManagementResource(
            PhotoCreator photoCreator,
            PhotoUploadRequestFactory photoUploadRequestFactory,
            PhotoDeleter photoDeleter) {
        this.photoCreator = photoCreator;
        this.photoUploadRequestFactory = photoUploadRequestFactory;
        this.photoDeleter = photoDeleter;
    }

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDto> upload(@RequestParam("file") MultipartFile file, PhotoUploadMetadataDto photoUploadMetadataDto) {
        try {
            PhotoUploadRequest photoUploadRequest = photoUploadRequestFactory.createPhotoUploadRequest(file, photoUploadMetadataDto);
            PhotoDto photoDto = photoCreator.create(photoUploadRequest);
            return ResponseEntity.ok(photoDto);
        } catch (IOException e) {
            LOGGER.error("error reading uploaded file", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/delete/{photoId}", consumes = MediaType.ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDeletionResponseDto> delete(@PathVariable("photoId") UUID photoId) {
        PhotoDeletionResponse photoDeletionResponse = photoDeleter.deletePhoto(photoId);
        if (photoDeletionResponse.totalFailure()) {
            return ResponseEntity.ok(new PhotoDeletionResponseDto(false, "failed to delete record and file"));
        }
        if (!photoDeletionResponse.isFileDeleted()) {
            return ResponseEntity.ok(fileDeletionFailed());
        }
        if (!photoDeletionResponse.isRecordDeleted()) {
            return ResponseEntity.ok(recordDeletionFailed());
        }
        return ResponseEntity.ok(new PhotoDeletionResponseDto(photoDeletionResponse.isSuccess()));
    }
}
