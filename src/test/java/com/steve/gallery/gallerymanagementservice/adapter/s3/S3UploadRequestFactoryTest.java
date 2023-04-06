package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequestBuilder.aPhotoUploadRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class S3UploadRequestFactoryTest {

    @Test
    public void shouldCreateS3UploadRequest() throws IOException {
        String bucketName = "bucketName";
        File file = aFile();

        S3UploadRequestFactory underTest = new S3UploadRequestFactory();

        PhotoUploadRequest photoUploadRequest = aPhotoUploadRequest().withPhoto(file).build();
        S3UploadRequest result = underTest.create(bucketName, photoUploadRequest);

        ObjectMetadata expectedMetadata = new ObjectMetadata();
        expectedMetadata.setContentLength(file.length());
        expectedMetadata.setContentType("image/jpeg");
        assertThat(result.getUploadId(), notNullValue());
        assertThat(result.getPutObjectRequest().getBucketName(), is(bucketName));
        assertThat(result.getPutObjectRequest().getMetadata().getContentLength(), is(file.length()));
    }

    private File aFile() throws IOException {
        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());

        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        photo.deleteOnExit();
        return photo;
    }

}