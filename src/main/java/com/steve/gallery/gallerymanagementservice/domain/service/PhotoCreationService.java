package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoCreationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoCreationService.class);

    private final PhotoRepository photoRepository;
    private final PhotoFactory photoFactory;
    private final DtoFactory<PhotoDto> photoDtoFactory;
    private final UploadResource uploadResource;

    public PhotoCreationService(
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
