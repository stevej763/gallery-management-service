package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.adater.DtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.adater.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.adater.UploadResource;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.UploadedPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoCreator.class);

    private final PhotoRepository photoRepository;
    private final PhotoFactory photoFactory;
    private final DtoFactory<PhotoDto> photoDtoFactory;
    private final UploadResource uploadResource;

    public PhotoCreator(
            PhotoRepository photoRepository,
            PhotoFactory photoFactory,
            DtoFactory<PhotoDto> photoDtoFactory,
            UploadResource uploadResource) {
        this.photoRepository = photoRepository;
        this.photoFactory = photoFactory;
        this.photoDtoFactory = photoDtoFactory;
        this.uploadResource = uploadResource;
    }

    public PhotoDto create(PhotoUploadRequest photoUploadRequest) {
        UploadedPhoto upload = uploadResource.upload(photoUploadRequest);
        Photo photo = photoFactory.convert(upload);
        Photo savedPhoto = photoRepository.save(photo);
        PhotoDto photoDto = photoDtoFactory.convert(savedPhoto);
        LOGGER.info("photo creation request successful photoId={} uploadId={}", photoDto.getPhotoId(), photoDto.getUploadId());
        return photoDto;
    }
}
