package com.steve.gallery.gallerymanagementservice.domain;

import java.util.List;

public interface PhotoRepository {

    List<Photo> findAll();
}
