package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    public Photo addTag(TagRequest request) {
        Photo originalPhoto = photoFinder.findPhotoById(request.getPhotoId());
        if (!tagIsPresent(request, originalPhoto)) {
            Photo updatedPhoto = photoRepository.addTag(request);
            LOGGER.info("photo tags updated photoId={} from={} to={}",
                        updatedPhoto.getPhotoId(), originalPhoto.getTags(), updatedPhoto.getTags());
            return updatedPhoto;
        }
        LOGGER.info("tag request ignored, tag already exists for photoId={} tagRequest={}",
                    originalPhoto.getPhotoId(), request.getTagName());
        return originalPhoto;
    }

    public Photo removeTag(TagRequest request) {
        Photo originalPhoto = photoFinder.findPhotoById(request.getPhotoId());
        if (tagIsPresent(request, originalPhoto)) {
            Photo updatedPhoto = photoRepository.removeTag(request);
            LOGGER.info("photo tags updated photoId={} from={} to={}",
                        updatedPhoto.getPhotoId(), originalPhoto.getTags(), updatedPhoto.getTags());
            return updatedPhoto;
        }
        LOGGER.info("tag request ignored, no matching tag on photoId={} tagRequest={}",
                    originalPhoto.getPhotoId(), request.getTagName());
        return originalPhoto;
    }

    private boolean tagIsPresent(TagRequest request, Photo originalPhoto) {
        return originalPhoto.getTags().contains(request.getTagName());
    }

    private Photo updatePhoto(String modifiedTitle, Photo originalPhoto) {
        return new PhotoBuilder(originalPhoto)
                .withTitle(modifiedTitle)
                .build();
    }

    public List<Photo> removeCategoryFromAllPhotos(CategoryDeletionRequest categoryDeletionRequest) {
        List<Photo> photos = photoFinder.findAllByCategory(categoryDeletionRequest.getCategoryId());
        return photos.stream()
                     .map(photo -> removeCategoryFromPhoto(categoryDeletionRequest, photo))
                     .toList();
    }

    private Photo removeCategoryFromPhoto(CategoryDeletionRequest categoryDeletionRequest, Photo photo) {
        CategoryRequest categoryRequest = new CategoryRequest(photo.getPhotoId(), categoryDeletionRequest.getCategoryId());
        return photoRepository.removeCategory(categoryRequest);
    }
}
