package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

public class PhotoFactory {
    public Photo convert(PhotoUploadRequest photoUploadRequest) {
        return new PhotoBuilder()
                .withPhotoId(UUID.randomUUID())
                .withTitle(photoUploadRequest.getTitle())
                .withDescription(photoUploadRequest.getDescription())
                .withTags(photoUploadRequest.getTags())
                .withCategories(photoUploadRequest.getCategories())
                .build();
    }
}
