package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class MongoPhotoRepository implements PhotoRepository {

    public static final String PHOTO_COLLECTION = "photos";
    private final MongoTemplate mongoTemplate;

    public MongoPhotoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Photo> findAll() {
        return mongoTemplate.findAll(Photo.class, PHOTO_COLLECTION);
    }
}
