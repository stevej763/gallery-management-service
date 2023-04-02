package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class PhotoFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoFinder.class);

    private final PhotoRepository photoRepository;

    public PhotoFinder(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        List<Photo> photos = photoRepository.findAll();
        LOGGER.info("search for all photos made totalPhotos={}", photos.size());
        return photos;
    }

    public Photo findPhotoById(UUID photoId) {
        Photo photo = photoRepository.findById(photoId);
        LOGGER.info("search made for photoId={}", photo.getPhotoId());
        return photo;
    }

    public List<Photo> findPhotoByTitle(String title) {
        List<Photo> photosByTitle = photoRepository.findByTitle(title);
        LOGGER.info("search made by title={} matches={}", title, photosByTitle.size());
        return photosByTitle;
    }
}
