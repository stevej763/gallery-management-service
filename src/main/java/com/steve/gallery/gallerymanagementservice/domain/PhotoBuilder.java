package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

public class PhotoBuilder {
    private UUID photoId = UUID.randomUUID();

    public PhotoBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public Photo build() {
        return new Photo(photoId);
    }
}