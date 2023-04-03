package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.DescriptionEditRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TagRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TitleEditRequestDto;
import com.steve.gallery.gallerymanagementservice.domain.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.TagRequest;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/admin/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoDetailsModificationResource {

    private final PhotoDetailsEditor photoDetailsEditor;
    private final PhotoDtoFactory photoDtoFactory;

    public PhotoDetailsModificationResource(PhotoDetailsEditor photoDetailsEditor, PhotoDtoFactory photoDtoFactory) {
        this.photoDetailsEditor = photoDetailsEditor;
        this.photoDtoFactory = photoDtoFactory;
    }

    @PostMapping(value = "{photoId}/title")
    public ResponseEntity<PhotoDto> editTitle(@PathVariable("photoId") UUID photoId,
                                              @RequestBody TitleEditRequestDto titleEditRequestDto) {
        TitleEditRequest titleEditRequest = new TitleEditRequest(photoId, titleEditRequestDto.getTitleChange());
        Photo photo = photoDetailsEditor.editTitle(titleEditRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping(value = "{photoId}/description")
    public ResponseEntity<PhotoDto> editDescription(@PathVariable("photoId") UUID photoId,
                                                    @RequestBody DescriptionEditRequestDto descriptionEditRequestDto) {
        DescriptionEditRequest editRequest = new DescriptionEditRequest(photoId, descriptionEditRequestDto.getDescriptionChange());
        Photo photo = photoDetailsEditor.editDescription(editRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping(value = "{photoId}/tag/add")
    public ResponseEntity<PhotoDto> addTag(@PathVariable("photoId") UUID photoId,
                                           @RequestBody TagRequestDto tagRequestDto) {
        TagRequest tagRequest = new TagRequest(photoId, tagRequestDto.getValue());
        Photo photo = photoDetailsEditor.addTag(tagRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping(value = "{photoId}/tag/delete")
    public ResponseEntity<PhotoDto> deleteTag(@PathVariable("photoId") UUID photoId,
                                              @RequestBody TagRequestDto tagRequestDto) {
        TagRequest tagRequest = new TagRequest(photoId, tagRequestDto.getValue());
        Photo photo = photoDetailsEditor.removeTag(tagRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }
}
