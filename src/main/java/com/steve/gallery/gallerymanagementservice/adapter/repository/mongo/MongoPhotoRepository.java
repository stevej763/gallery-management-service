package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import java.util.List;
import java.util.UUID;

public class MongoPhotoRepository implements PhotoRepository {

  private final PhotoDao photoDao;

  public MongoPhotoRepository(PhotoDao photoDao) {
    this.photoDao = photoDao;
  }

  @Override
  public List<Photo> findAll() {
    return photoDao.findAllPhotos();
  }

  @Override
  public Photo findById(UUID photoId) {
    return photoDao.findPhotoById(photoId);
  }
}
