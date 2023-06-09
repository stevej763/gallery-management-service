package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CategoryMetadata {

    public static final String CATEGORY_ID = "categoryId";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String CREATED_AT = "createdAt";
    public static final String MODIFIED_AT = "modifiedAt";
    @Id
    private final UUID categoryId;

    private final String title;
    private final String subtitle;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CategoryMetadata(
            @JsonProperty(CATEGORY_ID) UUID categoryId,
            @JsonProperty(TITLE) String title,
            @JsonProperty(SUBTITLE) String subtitle,
            @JsonProperty(CREATED_AT) LocalDateTime createdAt,
            @JsonProperty(MODIFIED_AT) LocalDateTime modifiedAt) {
        this.categoryId = categoryId;
        this.title = title;
        this.subtitle = subtitle;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public UUID getCategoryId() {
        return categoryId;
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
