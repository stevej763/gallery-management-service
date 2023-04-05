package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoMetadataBuilder {

    private UUID photoId = UUID.randomUUID();
    private String title;
    private String description;
    private List<String> tags;
    private List<UUID> categories;
    private UUID uploadId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public static PhotoMetadataBuilder aPhotoMetadata() {
        return new PhotoMetadataBuilder();
    }

    public PhotoMetadataBuilder() {
    }

    public PhotoMetadataBuilder withPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoMetadataBuilder withTitle(final String title) {
        this.title = title;
        return this;
    }

    public PhotoMetadataBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PhotoMetadataBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PhotoMetadataBuilder withCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public PhotoMetadataBuilder withUploadId(UUID uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public PhotoMetadataBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PhotoMetadataBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public PhotoMetadata build() {
        return new PhotoMetadata(photoId, title, description, tags, categories, uploadId, createdAt, modifiedAt);
    }
}