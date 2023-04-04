package com.steve.gallery.gallerymanagementservice.integration.database;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao.PHOTO_COLLECTION;

@SpringBootTest
public abstract class BaseMongoIntegrationTest {

    public static final String BUCKET_NAME = UUID.randomUUID().toString();

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected AmazonS3 s3Client;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
        s3Client.listObjects(BUCKET_NAME)
                .getObjectSummaries()
                .forEach(s3ObjectSummary -> s3Client.deleteObject(BUCKET_NAME, s3ObjectSummary.getKey()));
        s3Client.deleteBucket(new DeleteBucketRequest(BUCKET_NAME));
    }

    @BeforeEach
    void setUp() {
        s3Client.createBucket(BUCKET_NAME);
        mongoTemplate.getDb().drop();
    }

    protected void savePhotoToDatabase(PhotoMetadata photoMetadata) {
        mongoTemplate.save(photoMetadata, PHOTO_COLLECTION);
    }

    protected void saveCategoryToDatabase(CategoryMetadata categoryMetadata) {
        mongoTemplate.save(categoryMetadata, CategoryDao.CATEGORY_COLLECTION);
    }

}
