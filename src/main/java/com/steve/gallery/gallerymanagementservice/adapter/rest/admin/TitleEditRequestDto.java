package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class TitleEditRequestDto {

    private final UUID photoId;
    private final String title;

    public TitleEditRequestDto(@JsonProperty("photoId") UUID photoId, @JsonProperty("titleChange") String titleChange) {
        this.photoId = photoId;
        this.title = titleChange;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
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
