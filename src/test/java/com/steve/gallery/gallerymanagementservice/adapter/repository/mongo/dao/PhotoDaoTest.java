package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.dao;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;
import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class PhotoDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void retrievesAllPhotosFromPhotosCollection() {
        Photo photo1 = createPhoto();
        Photo photo2 = createPhoto();
        Photo photo3 = createPhoto();
        mongoTemplate.save(photo1, PHOTO_COLLECTION);
        mongoTemplate.save(photo2, PHOTO_COLLECTION);
        mongoTemplate.save(photo3, PHOTO_COLLECTION);

        PhotoDao underTest = new PhotoDao(mongoTemplate);
        List<Photo> result = underTest.findAllPhotos();

        List<Photo> expected = List.of(photo1, photo2, photo3);
        assertThat(result, is(expected));
    }

    private static Photo createPhoto() {
        return aPhoto()
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    @Test
    public void retrievesAllPhotosWithMatchingTitle() {
        String photoTitle = "title";
        Photo photo = new PhotoBuilder()
                .withTitle(photoTitle)
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        PhotoDao underTest = new PhotoDao(mongoTemplate);
        List<Photo> result = underTest.findPhotoByTitle(photoTitle);

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesAllPhotosWithCaseInsensitivity() {
        String photoTitle = "TiTle";
        Photo photo = new PhotoBuilder()
                .withTitle(photoTitle)
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        PhotoDao underTest = new PhotoDao(mongoTemplate);
        List<Photo> result = underTest.findPhotoByTitle("title");

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void retrievesPhotoById() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new PhotoBuilder()
                .withPhotoId(photoId)
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        mongoTemplate.save(photo, PHOTO_COLLECTION);

        PhotoDao underTest = new PhotoDao(mongoTemplate);
        Photo result = underTest.findPhotoById(photoId);
        assertThat(result, is(photo));
    }

    @Test
    public void savesPhoto() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new PhotoBuilder().withPhotoId(photoId).build();

        PhotoDao underTest = new PhotoDao(mongoTemplate);
        Photo result = underTest.save(photo);
        assertThat(result, is(photo));
    }
}
