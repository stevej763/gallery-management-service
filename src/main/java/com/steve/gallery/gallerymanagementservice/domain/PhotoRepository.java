package com.steve.gallery.gallerymanagementservice.domain;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository {

    List<Photo> findAll();

    Photo findById(UUID photoId);

    List<Photo> findByTitle(String title);

    Photo save(Photo photo);

    boolean delete(UUID photoId);

    Photo updateTitle(TitleEditRequest result);

    Photo updateDescription(DescriptionEditRequest photo);

    Photo addTag(TagRequest tagRequest);

    Photo removeTag(TagRequest tagRequest);
}
