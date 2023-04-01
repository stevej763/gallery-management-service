package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;

import java.util.List;

public class MongoPhotoRepository implements PhotoRepository {

    public static final String PHOTO_COLLECTION = "photos";
    private final PhotoDao photoDao;

    public MongoPhotoRepository(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    public List<Photo> findAll() {
        return photoDao.findAllPhotos();
    }
}
