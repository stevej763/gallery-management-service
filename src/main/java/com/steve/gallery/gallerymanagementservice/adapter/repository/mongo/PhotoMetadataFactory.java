package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;

public class PhotoMetadataFactory {

    public PhotoMetadata convert(Photo photo) {
        return new PhotoMetadata(
                photo.getPhotoId(),
                photo.getTitle(),
                photo.getDescription(),
                photo.getTags(),
                photo.getCategories(),
                photo.getUploadId(),
                photo.getCreatedAt(),
                photo.getModifiedAt());
    }
}
