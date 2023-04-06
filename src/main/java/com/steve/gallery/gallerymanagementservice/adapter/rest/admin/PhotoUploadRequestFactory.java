package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUploadRequestFactory {

    public PhotoUploadRequest createPhotoUploadRequest(
            MultipartFile uploadedFile,
            PhotoUploadMetadataDto photoUploadMetadataDto) throws IOException {
        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        return new PhotoUploadRequestBuilder()
                .withTitle(photoUploadMetadataDto.getTitle())
                .withDescription(photoUploadMetadataDto.getDescription())
                .withTags(photoUploadMetadataDto.getTags())
                .withCategories(photoUploadMetadataDto.getCategories())
                .withPhoto(photo)
                .build();
    }
}
