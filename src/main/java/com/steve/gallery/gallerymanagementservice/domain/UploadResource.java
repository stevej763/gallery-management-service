package com.steve.gallery.gallerymanagementservice.domain;

import java.io.FileNotFoundException;

public interface UploadResource {

    UploadedPhoto upload(PhotoUploadRequest photoFile);

}
