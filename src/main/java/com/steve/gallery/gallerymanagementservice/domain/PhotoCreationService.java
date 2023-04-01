package com.steve.gallery.gallerymanagementservice.domain;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;

public class PhotoCreationService {

    private final PhotoRepository photoRepository;
    private final PhotoFactory photoFactory;
    private final DtoFactory<PhotoDto> photoDtoFactory;

    public PhotoCreationService(PhotoRepository photoRepository, PhotoFactory photoFactory, DtoFactory<PhotoDto> photoDtoFactory) {
        this.photoRepository = photoRepository;
        this.photoFactory = photoFactory;
        this.photoDtoFactory = photoDtoFactory;
    }

    public PhotoDto create(PhotoUploadRequest photoUploadRequest) {
        Photo photo = photoFactory.convert(photoUploadRequest);
        Photo savedPhoto = photoRepository.save(photo);
        return photoDtoFactory.convert(savedPhoto);
    }
}
