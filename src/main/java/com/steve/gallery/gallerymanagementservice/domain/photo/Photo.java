package com.steve.gallery.gallerymanagementservice.domain.photo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class Photo {

    private final UUID photoId;
    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<UUID> categories;
    private final UUID uploadId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public Photo(UUID photoId,
                  String title,
                 String description,
                 List<String> tags,
                 List<UUID> categories,
                 UUID uploadId,
                 LocalDateTime createdAt,
                 LocalDateTime modifiedAt) {
        this.photoId = photoId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.uploadId = uploadId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public UUID getPhotoId() {
        return photoId;
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

    public UUID getUploadId() {
        return uploadId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
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
