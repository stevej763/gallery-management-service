package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.*;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata.*;

public class MongoPhotoRepository implements PhotoRepository {

    private final PhotoDao photoDao;
    private final PhotoFactory photoFactory;
    private final PhotoMetadataFactory photoMetadataFactory;

    public MongoPhotoRepository(PhotoDao photoDao, PhotoFactory photoFactory, PhotoMetadataFactory photoMetadataFactory) {
        this.photoDao = photoDao;
        this.photoFactory = photoFactory;
        this.photoMetadataFactory = photoMetadataFactory;
    }

    @Override
    public List<Photo> findAll() {
        List<PhotoMetadata> allPhotos = photoDao.findAllPhotos();
        return allPhotos.stream().map(photoFactory::convert).toList();
    }

    @Override
    public Photo findById(UUID photoId) {
        PhotoMetadata photoMetadata = photoDao.findPhotoById(photoId);
        return photoFactory.convert(photoMetadata);
    }

    @Override
    public List<Photo> findByTitle(String title) {
        List<PhotoMetadata> photoByTitle = photoDao.findPhotoByTitle(title);
        return photoByTitle.stream().map(photoFactory::convert).toList();
    }

    @Override
    public Photo save(Photo photo) {
        PhotoMetadata photoMetadata = photoMetadataFactory.convert(photo);
        PhotoMetadata savedPhoto = photoDao.save(photoMetadata);
        return photoFactory.convert(savedPhoto);
    }

    @Override
    public boolean delete(UUID photoId) {
        return photoDao.delete(photoId);
    }

    @Override
    public Photo updateTitle(TitleEditRequest request) {
        photoDao.updateFieldForId(request.getPhotoId(), TITLE, request.getTitleChange());
        PhotoMetadata photoMetadata = photoDao.findPhotoById(request.getPhotoId());
        return photoFactory.convert(photoMetadata);
    }

    @Override
    public Photo updateDescription(DescriptionEditRequest request) {
        photoDao.updateFieldForId(request.getPhotoId(), DESCRIPTION, request.getDescriptionChange());
        PhotoMetadata photoMetadata = photoDao.findPhotoById(request.getPhotoId());
        return photoFactory.convert(photoMetadata);
    }

    @Override
    public Photo addTag(TagRequest request) {
        photoDao.push(request.getPhotoId(), TAGS, request.getTagName());
        PhotoMetadata photoMetadata = photoDao.findPhotoById(request.getPhotoId());
        return photoFactory.convert(photoMetadata);
    }

    @Override
    public Photo removeTag(TagRequest request) {
        photoDao.pull(request.getPhotoId(), TAGS, request.getTagName());
        PhotoMetadata photoMetadata = photoDao.findPhotoById(request.getPhotoId());
        return photoFactory.convert(photoMetadata);
    }
}
