package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import org.springframework.web.multipart.MultipartFile;

public class PhotoUploadRequestFactory {
    public PhotoUploadRequest createPhotoUploadRequest(MultipartFile file, PhotoUploadMetadataDto photoUploadMetadataDto) {
        return new PhotoUploadRequest(photoUploadMetadataDto.getTitle(),
                                      photoUploadMetadataDto.getDescription(),
                                      photoUploadMetadataDto.getTags(),
                                      photoUploadMetadataDto.getCategories());
    }
}
