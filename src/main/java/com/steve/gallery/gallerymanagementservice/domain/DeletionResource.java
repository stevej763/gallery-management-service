package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

public interface DeletionResource {

    boolean deleteFile(UUID uploadId);
}
