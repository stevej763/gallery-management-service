package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.dao;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class PhotoDaoTest {

    private static final String PHOTO_TITLE = "photoTitle";
    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    public static final List<UUID> CATEGORIES = List.of(CATEGORY_ID);
    public static final List<String> TAGS = List.of("string");

    @Autowired
    MongoTemplate mongoTemplate;
    private PhotoDao underTest;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @BeforeEach
    void setUp() {
        underTest = new PhotoDao(mongoTemplate);
    }

    @Test
    public void retrievesAllPhotosFromPhotosCollection() {
        PhotoMetadata photo1 = createPhotoMetadata(UUID.randomUUID(), "title1", TAGS, CATEGORIES);
        PhotoMetadata photo2 = createPhotoMetadata(UUID.randomUUID(), "title2", TAGS, CATEGORIES);
        PhotoMetadata photo3 = createPhotoMetadata(UUID.randomUUID(), "title3", TAGS, CATEGORIES);
        mongoTemplate.save(photo1, PHOTO_COLLECTION);
        mongoTemplate.save(photo2, PHOTO_COLLECTION);
        mongoTemplate.save(photo3, PHOTO_COLLECTION);

        List<PhotoMetadata> result = underTest.findAllPhotos();

        List<PhotoMetadata> expected = List.of(photo1, photo2, photo3);
        assertThat(result, is(expected));
    }

    @Test
    public void canFindAllByCategory() {
        PhotoMetadata photo1 = createPhotoMetadata(UUID.randomUUID(), "title1", TAGS, CATEGORIES);
        PhotoMetadata photo2 = createPhotoMetadata(UUID.randomUUID(), "title2", TAGS, CATEGORIES);
        PhotoMetadata photo3 = createPhotoMetadata(UUID.randomUUID(), "title3", TAGS, CATEGORIES);
        mongoTemplate.save(photo1, PHOTO_COLLECTION);
        mongoTemplate.save(photo2, PHOTO_COLLECTION);
        mongoTemplate.save(photo3, PHOTO_COLLECTION);

        List<PhotoMetadata> result = underTest.findByCategoryId(CATEGORY_ID);
        List<PhotoMetadata> expected = List.of(photo1, photo2, photo3);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesAllPhotosWithMatchingTitle() {
        PhotoMetadata photo = createPhotoMetadata();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        List<PhotoMetadata> result = underTest.findPhotoByTitle(PHOTO_TITLE);

        List<PhotoMetadata> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesAllPhotosWithCaseInsensitivity() {
        String photoTitle = "TiTle";
        PhotoMetadata photo = createPhotoMetadata(PHOTO_ID, photoTitle, TAGS, CATEGORIES);
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        List<PhotoMetadata> result = underTest.findPhotoByTitle("title");

        List<PhotoMetadata> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesPhotoById() {
        PhotoMetadata photo = createPhotoMetadata();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        PhotoMetadata result = underTest.findPhotoById(PHOTO_ID);
        assertThat(result, is(photo));
    }

    @Test
    public void canSavePhoto() {
        PhotoMetadata photo = createPhotoMetadata(PHOTO_ID, PHOTO_TITLE, TAGS, CATEGORIES);

        PhotoMetadata result = underTest.save(photo);
        assertThat(result, is(photo));
    }

    @Test
    public void canDeletePhoto() {
        PhotoMetadata photo = createPhotoMetadata(PHOTO_ID, PHOTO_TITLE, TAGS, CATEGORIES);
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        boolean result = underTest.delete(PHOTO_ID);
        assertThat(result, is(true));
    }

    @Test
    public void canUpdatePhoto() {
        PhotoMetadata currentPhoto = createPhotoMetadata(PHOTO_ID, PHOTO_TITLE, TAGS, CATEGORIES);
        mongoTemplate.save(currentPhoto, PHOTO_COLLECTION);

        boolean result = underTest.updateFieldForId(PHOTO_ID, "title", "updated value");
        assertThat(result, is(true));
    }

    @Test
    public void canPushValueIntoArray() {
        PhotoMetadata currentPhoto = createPhotoMetadata();
        mongoTemplate.save(currentPhoto);

        boolean result = underTest.push(PHOTO_ID, PhotoMetadata.TAGS, "new tag");
        assertThat(result, is(true));
    }

    @Test
    public void canPullValueOutOfArray() {
        PhotoMetadata currentPhoto = createPhotoMetadata();
        mongoTemplate.save(currentPhoto);

        boolean result = underTest.pull(PHOTO_ID, PhotoMetadata.TAGS, "new tag");
        assertThat(result, is(true));
    }

    @Test
    public void canRemoveCategory() {
        PhotoMetadata currentPhoto = createPhotoMetadata();
        mongoTemplate.save(currentPhoto);

        boolean result = underTest.removeCategory(PHOTO_ID, PhotoMetadata.CATEGORIES, CATEGORY_ID);
        assertThat(result, is(true));
    }

    private PhotoMetadata createPhotoMetadata() {
        return createPhotoMetadata(PHOTO_ID, PHOTO_TITLE, TAGS, CATEGORIES);
    }


    private PhotoMetadata createPhotoMetadata(UUID photoId, String photoTitle, List<String> tags, List<UUID> categories) {
        return new PhotoMetadataBuilder()
                .withTitle(photoTitle)
                .withPhotoId(photoId)
                .withTags(tags)
                .withCreatedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .withCategories(categories)
                .build();
    }
}
