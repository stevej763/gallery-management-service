package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import java.util.UUID;

public class PhotoDtoBuilder {

    private UUID photoId = UUID.randomUUID();

    public PhotoDtoBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoDto build() {
        return new PhotoDto(photoId);
    }
}
