package com.clone.twitter.userservice.service.cloud;

import com.amazonaws.services.s3.model.S3Object;
import lombok.NonNull;

import java.io.InputStream;

public interface S3Service {

    public void saveSvgToS3(String dicebearUrl, String bucketName, String fileName);

    public void uploadFile(String bucketName, String fileName, @NonNull InputStream inputStream);

    public S3Object getFile(String bucketName, String key);

    public void deleteFile(String bucketName, String key);
}
