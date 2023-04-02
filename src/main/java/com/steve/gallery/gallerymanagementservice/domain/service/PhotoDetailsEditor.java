package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;
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
        return photoRepository.updateTitle(titleEditRequest);
    }

    public Photo editDescription(DescriptionEditRequest descriptionEditRequest) {
        Photo originalPhoto = photoFinder.findPhotoById(descriptionEditRequest.getPhotoId());
        Photo updatedPhoto = photoRepository.updateDescription(descriptionEditRequest);
        LOGGER.info("photo description updated photoId={} from={} to={}",
                    updatedPhoto.getPhotoId(), originalPhoto.getDescription(), updatedPhoto.getDescription());
        return updatedPhoto;
    }

    private Photo updatePhoto(String modifiedTitle, Photo originalPhoto) {
        return new PhotoBuilder(originalPhoto)
                .withTitle(modifiedTitle)
                .build();
    }
}
