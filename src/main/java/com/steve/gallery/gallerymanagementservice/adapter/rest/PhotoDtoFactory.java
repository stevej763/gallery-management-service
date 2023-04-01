package com.steve.gallery.gallerymanagementservice.adapter.rest;

import com.steve.gallery.gallerymanagementservice.domain.DtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.Photo;

public class PhotoDtoFactory implements DtoFactory<PhotoDto> {

    private final String baseImageUrl;

    public PhotoDtoFactory(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    @Override
    public PhotoDto convert(Photo photo) {
        return new PhotoDtoBuilder()
                .withPhotoId(photo.getPhotoId())
                .withTitle(photo.getTitle())
                .withDescription(photo.getDescription())
                .withTags(photo.getTags())
                .withCategories(photo.getCategories())
                .withCreatedAt(photo.getCreatedAt())
                .withModifiedAt(photo.getModifiedAt())
                .withUploadId(photo.getUploadId())
                .withOriginalImageUrl(baseImageUrl + "/" + photo.getUploadId())
                .build();
    }
}
