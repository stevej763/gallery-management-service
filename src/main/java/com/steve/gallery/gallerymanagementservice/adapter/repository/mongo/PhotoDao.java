package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class PhotoDao {

    public static final String PHOTO_COLLECTION = "photos";
    private final MongoTemplate mongoTemplate;

    public PhotoDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Photo> findAllPhotos() {
        return mongoTemplate.findAll(Photo.class, PHOTO_COLLECTION);
    }

    public Photo findPhotoById(UUID photoId) {
        return mongoTemplate.findById(photoId, Photo.class, PHOTO_COLLECTION);
    }

    public List<Photo> findPhotoByTitle(String photoTitle) {
        Pattern caseInsensitiveSearch = compile(quote(photoTitle), CASE_INSENSITIVE);
        Query title = query(where("title").regex(caseInsensitiveSearch));
        return mongoTemplate.find(title, Photo.class, PHOTO_COLLECTION);
    }

    public Photo save(Photo photo) {
        return mongoTemplate.save(photo, PHOTO_COLLECTION);
    }
}
