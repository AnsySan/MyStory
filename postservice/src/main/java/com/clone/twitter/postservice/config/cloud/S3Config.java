package com.clone.twitter.postservice.config.cloud;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@Data
public class S3Config {

    @Value("${services.s3.accessKey}")
    private String accessKey;
    @Value("${services.s3.secretKey}")
    private String secretKey;
    @Value("${services.s3.endpoint}")
    private String endpoint;
    @Value("${services.s3.bucket-name}")
    private String bucketName;
    @Value("${services.s3.targetWidth}")
    private int wigth;
    @Value("${services.s3.targetHeight}")
    private int heigth;
    @Value("${services.s3.maxFilesAmount}")
    private int maxFiles;


    @Bean
    public AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey

        );

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration( new AwsClientBuilder.EndpointConfiguration( endpoint, null ) )
                .withCredentials( new AWSStaticCredentialsProvider( credentials ) )
                .build();

    }

    @Bean
    public String bucketName() {
        return bucketName;
    }
}