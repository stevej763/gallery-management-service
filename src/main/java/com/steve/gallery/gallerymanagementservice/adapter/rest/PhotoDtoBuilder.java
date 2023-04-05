package com.steve.gallery.gallerymanagementservice.adapter.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoDtoBuilder {

    private UUID photoId = UUID.randomUUID();
    private String title;
    private String description;
    private List<String> tags;
    private List<UUID> categories;
    private LocalDateTime createdAt = LocalDateTime.now();
    private UUID uploadId;
    private LocalDateTime modifiedAt = LocalDateTime.now();
    private String url;

    public static PhotoDtoBuilder aPhotoDto() {
        return new PhotoDtoBuilder();
    }

    public PhotoDtoBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PhotoDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PhotoDtoBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PhotoDtoBuilder withCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public PhotoDtoBuilder withUploadId(UUID uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public PhotoDtoBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PhotoDtoBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public PhotoDtoBuilder withOriginalImageUrl(String url) {
        this.url = url;
        return this;
    }

    public PhotoDto build() {
        return new PhotoDto(photoId, title, description, tags, categories, uploadId, url, createdAt, modifiedAt);
    }
}
