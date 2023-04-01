package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.PhotoSearchResource;
import com.steve.gallery.gallerymanagementservice.domain.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/v1/admin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class PhotoManagementResource {

    Logger LOGGER = LoggerFactory.getLogger(PhotoManagementResource.class);

    private final PhotoCreationService photoCreationService;
    private final PhotoUploadRequestFactory photoUploadRequestFactory;

    public PhotoManagementResource(PhotoCreationService photoCreationService, PhotoUploadRequestFactory photoUploadRequestFactory) {
        this.photoCreationService = photoCreationService;
        this.photoUploadRequestFactory = photoUploadRequestFactory;
    }

    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDto> upload(@RequestParam("file") MultipartFile file, PhotoUploadMetadataDto photoUploadMetadataDto) {
        try {
            PhotoUploadRequest photoUploadRequest = photoUploadRequestFactory.createPhotoUploadRequest(file, photoUploadMetadataDto);
            PhotoDto photoDto = photoCreationService.create(photoUploadRequest);
            return ResponseEntity.ok(photoDto);
        } catch (IOException e) {
            LOGGER.error("error reading uploaded file", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
