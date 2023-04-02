package com.steve.gallery.gallerymanagementservice.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoBuilder {

    private UUID photoId = UUID.randomUUID();
    private String title;
    private String description;
    private List<String> tags;
    private List<String> categories;
    private UUID uploadId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public static PhotoBuilder aPhoto() {
        return new PhotoBuilder();
    }

    public PhotoBuilder() {
    }

    public PhotoBuilder(Photo photo) {
        this.photoId = photo.getPhotoId();
        this.title = photo.getTitle();
        this.description = photo.getDescription();
        this.tags = photo.getTags();
        this.categories = photo.getCategories();
        this.uploadId = photo.getUploadId();
        this.createdAt = photo.getCreatedAt();
    }

    public PhotoBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoBuilder withTitle(final String title) {
        this.title = title;
        return this;
    }

    public PhotoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PhotoBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PhotoBuilder withCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public PhotoBuilder withUploadId(UUID uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public PhotoBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PhotoBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Photo build() {
        return new Photo(photoId, title, description, tags, categories, uploadId, createdAt, modifiedAt);
    }
}