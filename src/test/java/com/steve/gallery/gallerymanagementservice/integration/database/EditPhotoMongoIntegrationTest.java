package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataFactory;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.TagRequest;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder.aPhotoMetadata;
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
        PhotoMetadata originalRecord = aPhotoMetadata()
                .withPhotoId(PHOTO_ID)
                .withTitle("originalTitle")
                .withCreatedAt(originalTime)
                .withModifiedAt(originalTime)
                .build();
        savePhotoToDatabase(originalRecord);

        MongoPhotoRepository photoRepository = new MongoPhotoRepository(new PhotoDao(mongoTemplate), new PhotoFactory(), new PhotoMetadataFactory());
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

    @Test
    public void canAddTagToPhoto() {
        LocalDateTime originalTime = LocalDateTime.now().truncatedTo(SECONDS);
        PhotoMetadata originalRecord = aPhotoMetadata()
                .withPhotoId(PHOTO_ID)
                .withTitle("originalTitle")
                .withCreatedAt(originalTime)
                .withModifiedAt(originalTime)
                .withTags(List.of("original tag"))
                .build();
        savePhotoToDatabase(originalRecord);

        MongoPhotoRepository photoRepository = new MongoPhotoRepository(new PhotoDao(mongoTemplate), new PhotoFactory(), new PhotoMetadataFactory());
        PhotoDetailsEditor photoDetailsEditor = new PhotoDetailsEditor(new PhotoFinder(photoRepository), photoRepository);

        String tagToAdd = "updated tag";
        Photo updatedPhoto = photoDetailsEditor.addTag(new TagRequest(PHOTO_ID, tagToAdd));

        List<String> tagList = List.of("original tag", tagToAdd);
        Photo expected = aPhoto()
                .withPhotoId(PHOTO_ID)
                .withTitle("originalTitle")
                .withCreatedAt(originalTime)
                .withTags(tagList)
                .build();
        assertThat(updatedPhoto.getTags(), is(tagList));
        assertThat(updatedPhoto.getModifiedAt(), not(originalTime));
        assertThat(reflectionEquals(updatedPhoto, expected, "modifiedAt"), is(true));
    }
}
