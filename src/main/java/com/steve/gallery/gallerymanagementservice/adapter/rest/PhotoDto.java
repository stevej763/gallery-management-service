package com.steve.gallery.gallerymanagementservice.adapter.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoDto {

    private final UUID photoId;
    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<UUID> categories;
    private final UUID uploadId;
    private final String originalImageUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PhotoDto(@JsonProperty("photoId") UUID photoId,
                    @JsonProperty("title") String title,
                    @JsonProperty("description") String description,
                    @JsonProperty("tags") List<String> tags,
                    @JsonProperty("categories") List<UUID> categories,
                    @JsonProperty("uploadId") UUID uploadId,
                    @JsonProperty("originalImageUrl") String originalImageUrl,
                    @JsonProperty("createdAt") LocalDateTime createdAt,
                    @JsonProperty("modifiedAt") LocalDateTime modifiedAt) {
        this.photoId = photoId;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.uploadId = uploadId;
        this.originalImageUrl = originalImageUrl;
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

    public String getOriginalImageUrl() {
        return originalImageUrl;
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
