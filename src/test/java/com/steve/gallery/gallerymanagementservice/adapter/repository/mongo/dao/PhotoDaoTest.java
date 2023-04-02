package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.dao;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
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
        Photo photo1 = createPhoto(UUID.randomUUID(), "title1");
        Photo photo2 = createPhoto(UUID.randomUUID(), "title2");
        Photo photo3 = createPhoto(UUID.randomUUID(), "title3");
        mongoTemplate.save(photo1, PHOTO_COLLECTION);
        mongoTemplate.save(photo2, PHOTO_COLLECTION);
        mongoTemplate.save(photo3, PHOTO_COLLECTION);

        List<Photo> result = underTest.findAllPhotos();

        List<Photo> expected = List.of(photo1, photo2, photo3);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesAllPhotosWithMatchingTitle() {
        Photo photo = createPhoto();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        List<Photo> result = underTest.findPhotoByTitle(PHOTO_TITLE);

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesAllPhotosWithCaseInsensitivity() {
        String photoTitle = "TiTle";
        Photo photo = createPhoto(PHOTO_ID, photoTitle);
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        List<Photo> result = underTest.findPhotoByTitle("title");

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesPhotoById() {
        Photo photo = createPhoto();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        Photo result = underTest.findPhotoById(PHOTO_ID);
        assertThat(result, is(photo));
    }

    @Test
    public void canSavePhoto() {
        Photo photo = new PhotoBuilder().withPhotoId(PHOTO_ID).build();

        Photo result = underTest.save(photo);
        assertThat(result, is(photo));
    }

    @Test
    public void canDeletePhoto() {
        UUID photoId = PHOTO_ID;
        Photo photo = createPhoto(photoId, PHOTO_TITLE);
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        boolean result = underTest.delete(photoId);
        assertThat(result, is(true));
    }

    @Test
    public void canUpdatePhoto() {
        UUID photoId = PHOTO_ID;
        Photo currentPhoto = createPhoto(photoId, PHOTO_TITLE);
        mongoTemplate.save(currentPhoto, PHOTO_COLLECTION);

        boolean result = underTest.updateFieldForId(photoId, "title", "updated value");
        assertThat(result, is(true));
    }

    private Photo createPhoto() {
        return createPhoto(PHOTO_ID, PHOTO_TITLE);
    }

    private Photo createPhoto(UUID photoId, String photoTitle) {
        return new PhotoBuilder()
                .withTitle(photoTitle)
                .withPhotoId(photoId)
                .withCreatedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .build();
    }
}
