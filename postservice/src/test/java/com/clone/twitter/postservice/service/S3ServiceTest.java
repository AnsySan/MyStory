package com.clone.twitter.postservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.clone.twitter.postservice.config.S3Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;
    @InjectMocks
    private S3Service S3Service;
    @Mock
    private S3Config s3Config;

    @Test
    void successUploadFile() {
        MultipartFile file = mock(MultipartFile.class);
        String uuid = S3Service.uploadFile(file);

        assertNotNull(UUID.fromString(uuid));

        verify(amazonS3, times(1)).putObject(any(), any(), any(), any());
        verify(s3Config, times(1)).bucketName();
    }

    @Test
    void successDownloadFile() {
        S3Object s3Object = mock(S3Object.class);
        S3ObjectInputStream inputStreamMock = mock(S3ObjectInputStream.class);

        when(amazonS3.getObject(any(), anyString())).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(inputStreamMock);

        S3Service.downloadFile(UUID.randomUUID().toString());

        verify(amazonS3, times(1)).getObject(any(), anyString());
        verify(s3Object, times(1)).getObjectContent();
    }

    @Test
    void successDeleteFile() {
        S3Service.deleteFile(UUID.randomUUID().toString());

        verify(amazonS3, times(1)).deleteObject(any(), anyString());
    }
}