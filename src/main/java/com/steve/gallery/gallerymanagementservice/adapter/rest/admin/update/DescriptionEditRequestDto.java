package com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class DescriptionEditRequestDto {

    private final String descriptionChange;

    public DescriptionEditRequestDto(@JsonProperty("descriptionChange") String descriptionChange) {
        this.descriptionChange = descriptionChange;
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
