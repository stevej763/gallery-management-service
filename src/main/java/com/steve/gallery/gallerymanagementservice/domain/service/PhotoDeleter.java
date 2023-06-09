package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.adater.DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.adater.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PhotoDeleter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoDeleter.class);

    private final PhotoRepository photoRepository;
    private final DeletionResource deletionResource;

    public PhotoDeleter(PhotoRepository photoRepository, DeletionResource deletionResource) {
        this.photoRepository = photoRepository;
        this.deletionResource = deletionResource;
    }

    public PhotoDeletionResponse deletePhoto(UUID photoId) {
        Photo photo = photoRepository.findById(photoId);
        if (photo == null) {
            LOGGER.error("request to delete photo failed, no photo found photoId={}", photoId);
            return new PhotoDeletionResponse(photoId, false, false);
        }
        boolean recordDeleted = photoRepository.delete(photoId);
        boolean fileDeleted = deletionResource.deleteFile(photo.getUploadId());
        PhotoDeletionResponse photoDeletionResponse = new PhotoDeletionResponse(photoId, recordDeleted, fileDeleted);
        LOGGER.info("photo deletion request for photoId={} recordDeleted={} fileDeleted={}",
                    photoId, photoDeletionResponse.isRecordDeleted(), photoDeletionResponse.isFileDeleted());
        return photoDeletionResponse;
    }
}
