package com.steve.gallery.gallerymanagementservice.domain.adater;

import java.util.UUID;

public interface DeletionResource {

    boolean deleteFile(UUID uploadId);
}
