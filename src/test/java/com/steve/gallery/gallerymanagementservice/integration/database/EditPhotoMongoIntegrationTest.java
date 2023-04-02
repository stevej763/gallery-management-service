package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class EditPhotoMongoIntegrationTest extends BaseMongoIntegrationTest {

    public static final UUID PHOTO_ID = UUID.randomUUID();

    @Test
    public void canEditPhotoTitle() {
        LocalDateTime originalTime = LocalDateTime.now().truncatedTo(SECONDS);
        Photo originalRecord = aPhoto()
                .withPhotoId(PHOTO_ID)
                .withTitle("originalTitle")
                .withCreatedAt(originalTime)
                .withModifiedAt(originalTime)
                .build();
        savePhotoToDatabase(originalRecord);

        MongoPhotoRepository photoRepository = new MongoPhotoRepository(new PhotoDao(mongoTemplate));
        PhotoDetailsEditor photoDetailsEditor = new PhotoDetailsEditor(new PhotoFinder(photoRepository), photoRepository);

        String updatedTitle = "newTitle";
        Photo updatedPhoto = photoDetailsEditor.editTitle(new TitleEditRequest(PHOTO_ID, updatedTitle));

        Photo expected = aPhoto()
                .withPhotoId(PHOTO_ID)
                .withTitle(updatedTitle)
                .withCreatedAt(originalTime)
                .build();
        assertThat(updatedPhoto.getTitle(), is(updatedTitle));
        assertThat(updatedPhoto.getModifiedAt(), not(originalTime));
        assertThat(reflectionEquals(updatedPhoto, expected, "modifiedAt"), is(true));
    }
}
