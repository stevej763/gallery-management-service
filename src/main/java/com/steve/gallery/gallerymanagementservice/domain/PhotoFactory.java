package com.steve.gallery.gallerymanagementservice.domain;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;

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

    public Photo convert(UploadedPhoto upload) {
        return new PhotoBuilder()
                .withPhotoId(UUID.randomUUID())
                .withTitle(upload.getTitle())
                .withDescription(upload.getDescription())
                .withTags(upload.getTags())
                .withCategories(upload.getCategories())
                .withUploadId(upload.getUploadId())
                .build();
    }

    public Photo convert(PhotoMetadata photoMetadata) {
        return new Photo(
                photoMetadata.getPhotoId(),
                photoMetadata.getTitle(),
                photoMetadata.getDescription(),
                photoMetadata.getTags(),
                photoMetadata.getCategories(),
                photoMetadata.getUploadId(),
                photoMetadata.getCreatedAt(),
                photoMetadata.getModifiedAt());
    }
}
