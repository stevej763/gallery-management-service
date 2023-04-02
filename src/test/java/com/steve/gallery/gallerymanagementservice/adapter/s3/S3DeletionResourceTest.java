package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class S3DeletionResourceTest {

    private final AmazonS3 s3Client = mock(AmazonS3.class);
    private final String bucketName = "bucketName";

    @Test
    public void canDeleteFile() {
        S3DeletionResource underTest = new S3DeletionResource(s3Client, bucketName);

        boolean result = underTest.deleteFile(UUID.randomUUID());

        assertThat(result, is(true));
    }


    @Test
    public void returnsFalseWhenExceptionDeletingFile() {
        S3DeletionResource underTest = new S3DeletionResource(s3Client, bucketName);

        UUID uploadId = UUID.randomUUID();

        doThrow(new AmazonServiceException("error")).when(s3Client).deleteObject(any());
        boolean result = underTest.deleteFile(uploadId);

        assertThat(result, is(false));
    }

}