package com.clone.twitter.userservice.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
@PropertySource(ignoreResourceNotFound = true, value = "classpath:s3.properties")
public class S3Config {

    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${endpoint}")
    private String endpoint;
    @Value("${bucketName}")
    private String bucketName;


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