package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;

import java.util.UUID;

public class PhotoDeletionService {

    private final PhotoRepository photoRepository;
    private final DeletionResource deletionResource;

    public PhotoDeletionService(PhotoRepository photoRepository, DeletionResource deletionResource) {
        this.photoRepository = photoRepository;
        this.deletionResource = deletionResource;
    }

    public PhotoDeletionResponse deletePhoto(UUID photoId) {
        Photo photo = photoRepository.findById(photoId);
        boolean recordDeleted = photoRepository.delete(photoId);
        boolean fileDeleted = deletionResource.deleteFile(photo.getUploadId());
        return new PhotoDeletionResponse(photoId, recordDeleted, fileDeleted);
    }
}
