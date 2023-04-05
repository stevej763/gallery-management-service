package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoMetadata {

    public static final String PHOTO_ID = "photoId";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String TAGS = "tags";
    public static final String CATEGORIES = "categories";
    public static final String UPLOAD_ID = "uploadId";
    public static final String CREATED_AT = "createdAt";
    public static final String MODIFIED_AT = "modifiedAt";

    @Id
    private final UUID photoId;

    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<UUID> categories;
    private final UUID uploadId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PhotoMetadata(@JsonProperty(PHOTO_ID) UUID photoId,
                         @JsonProperty(TITLE) String title,
                         @JsonProperty(DESCRIPTION) String description,
                         @JsonProperty(TAGS) List<String> tags,
                         @JsonProperty(CATEGORIES) List<UUID> categories,
                         @JsonProperty(UPLOAD_ID) UUID uploadId,
                         @JsonProperty(CREATED_AT) LocalDateTime createdAt,
                         @JsonProperty(MODIFIED_AT) LocalDateTime modifiedAt) {
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
