package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
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

    public boolean delete(UUID photoId) {
        Query record = findByPhotoId(photoId);
        DeleteResult deleteResult = mongoTemplate.remove(record, Photo.class, PHOTO_COLLECTION);
        return deleteResult.getDeletedCount() == 1;
    }

    public boolean updateFieldForId(UUID photoId, String field, String value) {
        Update update = new Update()
                .set(field, value)
                .set("modifiedAt", LocalDateTime.now());
        Query query = findByPhotoId(photoId);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Photo.class, PHOTO_COLLECTION);
        return updateResult.getModifiedCount() == 1;
    }

    private Query findByPhotoId(UUID photoId) {
        return query(where("photoId").is(photoId));
    }
}
