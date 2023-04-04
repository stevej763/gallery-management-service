package com.steve.gallery.gallerymanagementservice.domain;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
