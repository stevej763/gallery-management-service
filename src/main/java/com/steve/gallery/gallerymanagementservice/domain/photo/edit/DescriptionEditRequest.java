package com.steve.gallery.gallerymanagementservice.domain.photo.edit;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class DescriptionEditRequest {

    private final UUID photoId;
    private final String descriptionChange;

    public DescriptionEditRequest(UUID photoId, String descriptionChange) {
        this.photoId = photoId;
        this.descriptionChange = descriptionChange;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public String getDescriptionChange() {
        return descriptionChange;
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
