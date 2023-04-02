package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoDetailsEditor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoDetailsEditor.class);

    private final PhotoFinder photoFinder;
    private final PhotoRepository photoRepository;

    public PhotoDetailsEditor(PhotoFinder photoFinder, PhotoRepository photoRepository) {
        this.photoFinder = photoFinder;
        this.photoRepository = photoRepository;
    }

    public Photo editTitle(TitleEditRequest titleEditRequest) {
        Photo originalPhoto = photoFinder.findPhotoById(titleEditRequest.getPhotoId());
        Photo updatedPhoto = updatePhoto(titleEditRequest.getTitleChange(), originalPhoto);
        LOGGER.info("photo title updated photoId={} from={} to={}",
                    updatedPhoto.getPhotoId(), originalPhoto.getTitle(), updatedPhoto.getTitle());
        return photoRepository.updateTitle(updatedPhoto);
    }

    private Photo updatePhoto(String modifiedTitle, Photo originalPhoto) {
        return new PhotoBuilder(originalPhoto)
                .withTitle(modifiedTitle)
                .build();
    }
}
