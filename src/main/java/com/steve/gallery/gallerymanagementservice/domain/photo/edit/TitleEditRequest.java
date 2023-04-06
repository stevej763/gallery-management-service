package com.steve.gallery.gallerymanagementservice.domain.photo.edit;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class TitleEditRequest {

    private final UUID photoId;
    private final String titleChange;

    public TitleEditRequest(UUID photoId, String titleChange) {
        this.photoId = photoId;
        this.titleChange = titleChange;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public String getTitleChange() {
        return titleChange;
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
