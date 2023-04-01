package com.steve.gallery.gallerymanagementservice.domain;

public interface DtoFactory<T> {

    T convert(Photo photo);
}
