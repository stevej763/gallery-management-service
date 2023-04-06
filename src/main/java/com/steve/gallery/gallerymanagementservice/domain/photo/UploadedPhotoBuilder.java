package com.steve.gallery.gallerymanagementservice.domain.photo;

import java.util.List;
import java.util.UUID;

public class UploadedPhotoBuilder {
    private String title;
    private String description;
    private List<String> tags;
    private List<UUID> category;
    private UUID uploadId;

    public static UploadedPhotoBuilder anUploadedPhoto() {
        return new UploadedPhotoBuilder();
    }

    public UploadedPhotoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public UploadedPhotoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UploadedPhotoBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public UploadedPhotoBuilder withCategories(List<UUID> category) {
        this.category = category;
        return this;
    }

    public UploadedPhotoBuilder withUploadId(UUID uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public UploadedPhoto build() {
        return new UploadedPhoto(title, description, tags, category, uploadId);
    }
}