package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;

import java.util.List;
import java.util.UUID;

public class PhotoFinder {

    private final PhotoRepository photoRepository;

    public PhotoFinder(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Photo findPhotoById(UUID photoId) {
        return photoRepository.findById(photoId);
    }

    public List<Photo> findPhotoByTitle(String title) {
        return photoRepository.findByTitle(title);
    }
}
