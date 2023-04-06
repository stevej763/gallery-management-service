package com.steve.gallery.gallerymanagementservice.domain.adater;

import com.steve.gallery.gallerymanagementservice.domain.category.CategoryRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TagRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TitleEditRequest;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository {

    List<Photo> findAll();

    List<Photo> findByTitle(String title);

    List<Photo> findAllByCategory(UUID categoryId);

    Photo findById(UUID photoId);

    Photo save(Photo photo);

    Photo updateTitle(TitleEditRequest result);

    Photo updateDescription(DescriptionEditRequest photo);

    Photo removeCategory(CategoryRequest categoryRequest);

    Photo addTag(TagRequest tagRequest);

    Photo removeTag(TagRequest tagRequest);

    boolean delete(UUID photoId);
}
