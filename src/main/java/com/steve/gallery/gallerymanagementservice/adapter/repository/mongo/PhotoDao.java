package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoTemplate;

public class PhotoDao {

  public static final String PHOTO_COLLECTION = "photos";
  private final MongoTemplate mongoTemplate;

  public PhotoDao(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<Photo> findAllPhotos() {
    return mongoTemplate.findAll(Photo.class, PHOTO_COLLECTION);
  }

  public Photo findPhotoById(UUID photoId) {
    return mongoTemplate.findById(photoId, Photo.class, PHOTO_COLLECTION);
  }
}
