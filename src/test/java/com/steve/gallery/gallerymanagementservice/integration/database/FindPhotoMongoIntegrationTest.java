package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.*;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FindPhotoMongoIntegrationTest extends BaseMongoIntegrationTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final PhotoMetadata PHOTO_METADATA = new PhotoMetadataBuilder()
            .withPhotoId(PHOTO_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    private static final Photo PHOTO = new PhotoBuilder()
            .withPhotoId(PHOTO_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();


    @Test
    public void canReturnAListOfPhotos() {
        savePhotoToDatabase(PHOTO_METADATA);
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao, new PhotoFactory(), new PhotoMetadataFactory());
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        List<Photo> result = photoFinder.findAll();

        List<Photo> photos = List.of(PHOTO);
        assertThat(result, is(photos));
    }

    @Test
    public void canReturnAPhotoById() {
        savePhotoToDatabase(PHOTO_METADATA);
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao, new PhotoFactory(), new PhotoMetadataFactory());
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        Photo result = photoFinder.findPhotoById(PHOTO_ID);

        assertThat(result, is(PHOTO));
    }

}
