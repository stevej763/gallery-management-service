package com.steve.gallery.gallerymanagementservice.domain;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoUploadRequest {

    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<UUID> categories;
    private final File photo;

    public PhotoUploadRequest(String title, String description, List<String> tags, List<UUID> categories, File photo) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<UUID> getCategories() {
        return categories;
    }

    public File getPhoto() {
        return photo;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
