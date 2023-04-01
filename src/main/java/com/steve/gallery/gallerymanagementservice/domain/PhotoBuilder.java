package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

public class PhotoBuilder {

    private UUID photoId = UUID.randomUUID();
    private String title;

    public PhotoBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoBuilder withTitle(final String title) {
        this.title = title;
        return this;
    }

    public Photo build() {
        return new Photo(photoId, title);
    }
}