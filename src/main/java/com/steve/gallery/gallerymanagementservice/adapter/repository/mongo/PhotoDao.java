package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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

    public static final String PHOTO_COLLECTION = "photoMetadata";
    private final MongoTemplate mongoTemplate;

    public PhotoDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<PhotoMetadata> findAllPhotos() {
        return mongoTemplate.findAll(PhotoMetadata.class, PHOTO_COLLECTION);
    }

    public PhotoMetadata findPhotoById(UUID photoId) {
        return mongoTemplate.findById(photoId, PhotoMetadata.class, PHOTO_COLLECTION);
    }

    public List<PhotoMetadata> findPhotoByTitle(String photoTitle) {
        Pattern caseInsensitiveSearch = compile(quote(photoTitle), CASE_INSENSITIVE);
        Query title = query(where(PhotoMetadata.TITLE).regex(caseInsensitiveSearch));
        return mongoTemplate.find(title, PhotoMetadata.class, PHOTO_COLLECTION);
    }

    public PhotoMetadata save(PhotoMetadata photo) {
        return mongoTemplate.save(photo, PHOTO_COLLECTION);
    }

    public boolean delete(UUID photoId) {
        Query record = findByPhotoId(photoId);
        DeleteResult deleteResult = mongoTemplate.remove(record, PhotoMetadata.class, PHOTO_COLLECTION);
        return deleteResult.getDeletedCount() == 1;
    }

    public boolean updateFieldForId(UUID photoId, String field, String value) {
        Update update = createUpdateMap(field, value);
        Query query = findByPhotoId(photoId);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, PhotoMetadata.class, PHOTO_COLLECTION);
        return updateResult.getModifiedCount() == 1;
    }

    private Update createUpdateMap(String field, String value) {
        return new Update()
                .set(field, value)
                .set(PhotoMetadata.MODIFIED_AT, LocalDateTime.now());
    }

    private Query findByPhotoId(UUID photoId) {
        return query(where("photoId").is(photoId));
    }
}
