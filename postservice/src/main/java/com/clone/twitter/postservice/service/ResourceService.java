package com.clone.twitter.postservice.service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.amazonaws.services.neptunedata.model.S3Exception;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.clone.twitter.postservice.dto.ResourceDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.Resource;
import com.clone.twitter.postservice.mapper.ResourceMapper;
import com.clone.twitter.postservice.repository.ResourceRepository;
import com.clone.twitter.postservice.validator.ResourceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceValidator resourceValidator;
    private final PostService postService;
    private final S3Service s3Service;

    @Transactional
    public Resource findById(int id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Resource with id %s not found", id)));
    }

    @Transactional
    public List<ResourceDto> create(int postId, int userId, List<MultipartFile> files) {

        Post post = postService.existsPost(postId);
        resourceValidator.validatePostAuthorAndResourceAuthor(post.getAuthorId(), userId);
        resourceValidator.validateCountFilesPerPost(postId, files.size());

        ExecutorService executorService = Executors.newFixedThreadPool(files.size());
        try(Closeable ignored = executorService::shutdown) {
            List<CompletableFuture<Resource>> resources = new ArrayList<>();
            List<Resource> savedResources = new ArrayList<>();

            files.forEach(file -> {
                CompletableFuture<Resource> resource = CompletableFuture.supplyAsync(() -> {
                    String key = s3Service.uploadFile(file);
                    return Resource.builder()
                            .name(file.getOriginalFilename())
                            .key(key)
                            .size(file.getSize())
                            .type(file.getContentType())
                            .post(post)
                            .build();
                }, executorService);
                resources.add(resource);
            });

            resources.forEach(resource -> {
                Resource resourceToSave = resource.join();
                savedResources.add(resourceRepository.save(resourceToSave));
            });

            log.info("Successfully create resource");
            return savedResources.stream()
                    .map(resourceMapper::toDto)
                    .toList();

        } catch (AmazonS3Exception | IOException ex) {
            log.error(ex.getMessage());
            throw new S3Exception(ex.getMessage());
        }
    }

    public InputStream downloadResource(String key) {
        resourceValidator.validateExistenceByKey(key);
        return s3Service.downloadFile(key);
    }

    @Transactional
    public void deleteFile(String key, int userId) {
        Resource resourceToRemove = resourceRepository.findByKey(key);
        Post post = resourceToRemove.getPost();

        resourceValidator.validatePostAuthorAndResourceAuthor(post.getAuthorId(), userId);
        resourceValidator.validateExistenceByKey(key);

        resourceRepository.deleteByKey(key);
        s3Service.deleteFile(key);

        log.error("Successfully delete file from resources");
    }
}