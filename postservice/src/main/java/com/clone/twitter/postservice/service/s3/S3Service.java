package com.clone.twitter.postservice.service.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface S3Service {

    String uploadFile(MultipartFile file);

    InputStream downloadFile(String key);

    void deleteFile(String key);

}
