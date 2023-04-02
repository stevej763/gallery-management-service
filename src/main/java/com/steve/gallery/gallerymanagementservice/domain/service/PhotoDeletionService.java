package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PhotoDeletionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoDeletionService.class);

    private final PhotoRepository photoRepository;
    private final DeletionResource deletionResource;

    public PhotoDeletionService(PhotoRepository photoRepository, DeletionResource deletionResource) {
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
