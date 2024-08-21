package com.clone.twitter.userservice.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final RestTemplate restTemplate;

    public void saveSvgToS3(String dicebearUrl, String bucketName, String fileName) {

        try {

            byte[] svgBytes = restTemplate.getForObject( dicebearUrl, byte[].class );
            ByteArrayInputStream inputStream = new ByteArrayInputStream( svgBytes );

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength( svgBytes.length );
            metadata.setContentType( "image/svg+xml" );

            amazonS3.putObject( new PutObjectRequest( bucketName, fileName, inputStream, metadata ) );
        } catch (Exception e) {
            log.error( e.getMessage() );
        }
    }

    public void uploadFile(String bucketName, String fileName, @NonNull InputStream inputStream){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        try {
            amazonS3.putObject(bucketName, fileName, inputStream, metadata);
        } catch (SdkClientException e) {
            log.error("Error uploading a file to Amazon S3", e);
            throw new RuntimeException("Error uploading a file to Amazon S3", e);
        }
    }

    public S3Object getFile(String bucketName, String key){
        S3Object s3Object;
        try {
            s3Object = amazonS3.getObject(bucketName, key);
        } catch (SdkClientException e) {
            log.error("Error getting a file from Amazon S3", e);
            throw new RuntimeException("Error getting a file from Amazon S3", e);
        }
        return s3Object;
    }

    public void deleteFile(String bucketName, String key){
        try {
            amazonS3.deleteObject(bucketName, key);
        } catch (SdkClientException e) {
            log.error("Error deleting a file from Amazon S3", e);
            throw new RuntimeException("Error deleting a file from Amazon S3", e);
        }
    }
}