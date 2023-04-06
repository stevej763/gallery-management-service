package com.steve.gallery.gallerymanagementservice.domain.adater;

import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;

public interface DtoFactory<T> {

    T convert(Photo photo);
}
