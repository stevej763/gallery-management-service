package com.steve.gallery.gallerymanagementservice;

import java.util.List;

public class PhotoFinder {

    private final PhotoRepository photoRepository;

    public PhotoFinder(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }
}
