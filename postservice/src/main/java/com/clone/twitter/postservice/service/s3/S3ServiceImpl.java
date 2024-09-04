package com.clone.twitter.postservice.service.s3;

import com.amazonaws.services.neptunedata.model.S3Exception;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.clone.twitter.postservice.config.cloud.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;
    private final S3Config s3Config;
    private final RestTemplate restTemplate;

    @Override
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

    @Override
    public String uploadFile(MultipartFile file) {

        String key = generateUniqueKey();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(s3Config.bucketName(), key, file.getInputStream(), metadata);
            log.info("Successfully upload file");
        } catch (IOException e) {
            log.error("Cant upload file to s3 storage");
            throw new S3Exception("Cant upload file to s3 storage");
        }

        return key;
    }

    @Override
    public InputStream downloadFile(String key) {
        return amazonS3.getObject(s3Config.bucketName(), key).getObjectContent();
    }

    @Override
    public void deleteFile(String key) {
        amazonS3.deleteObject(s3Config.bucketName(), key);
        log.info("Successfully delete file");
    }

    private String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}