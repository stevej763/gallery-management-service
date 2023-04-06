package com.steve.gallery.gallerymanagementservice.domain.photo;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class PhotoUploadRequestBuilder {
    private String title;
    private String description;
    private List<String> tags;
    private List<UUID> categories;
    private File photo;

    public static PhotoUploadRequestBuilder aPhotoUploadRequest() {
        return new PhotoUploadRequestBuilder();
    }

    public PhotoUploadRequestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PhotoUploadRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PhotoUploadRequestBuilder withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PhotoUploadRequestBuilder withCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public PhotoUploadRequestBuilder withPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    public PhotoUploadRequest build() {
        return new PhotoUploadRequest(title, description, tags, categories, photo);
    }
}