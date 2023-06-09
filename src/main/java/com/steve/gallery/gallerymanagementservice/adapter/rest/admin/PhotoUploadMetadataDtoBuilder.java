package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import java.util.List;
import java.util.UUID;

public class PhotoUploadMetadataDtoBuilder {
    private String title;
    private String description;
    private List<String> tags;
    private List<UUID> categories;

    public static PhotoUploadMetadataDtoBuilder aPhotoUploadMetadataDtoBuilder() {
        return new PhotoUploadMetadataDtoBuilder();
    }

    public PhotoUploadMetadataDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PhotoUploadMetadataDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PhotoUploadMetadataDtoBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PhotoUploadMetadataDtoBuilder withCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public PhotoUploadMetadataDto build() {
        return new PhotoUploadMetadataDto(title, description, tags, categories);
    }
}