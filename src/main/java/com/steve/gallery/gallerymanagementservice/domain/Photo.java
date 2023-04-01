package com.steve.gallery.gallerymanagementservice.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import org.springframework.data.annotation.Id;

public class Photo {

  @Id private final UUID photoId;

  public Photo(@JsonProperty("photoId") UUID photoId) {
    this.photoId = photoId;
  }

  public UUID getPhotoId() {
    return photoId;
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
