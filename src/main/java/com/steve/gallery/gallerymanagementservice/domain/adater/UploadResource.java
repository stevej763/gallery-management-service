package com.steve.gallery.gallerymanagementservice.domain.adater;

import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.UploadedPhoto;

public interface UploadResource {

    UploadedPhoto upload(PhotoUploadRequest photoFile);

}
