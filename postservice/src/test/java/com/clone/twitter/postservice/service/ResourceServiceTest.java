package com.clone.twitter.postservice.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.clone.twitter.postservice.dto.ResourceDto;
import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.entity.Resource;
import com.clone.twitter.postservice.mapper.ResourceMapper;
import com.clone.twitter.postservice.repository.ResourceRepository;
import com.clone.twitter.postservice.validator.ResourceValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;
    @Spy
    private ResourceMapper resourceMapper = Mappers.getMapper(ResourceMapper.class);
    @Mock
    private ResourceValidator resourceValidator;
    @Mock
    private S3Service amazonS3Service;
    @Mock
    private PostService postService;

    @InjectMocks
    private ResourceService resourceServiceImpl;

    @Test
    void successFindById() {
        Resource resource = Resource.builder().id(1).build();

        when(resourceRepository.findById(1)).thenReturn(Optional.ofNullable(resource));

        Resource result = resourceServiceImpl.findById(1);

        assertEquals(resource, result);
    }

    @Test
    void successCreate() {
        MultipartFile file = mock(MultipartFile.class);
        Post post = Post.builder().id(1).build();
        String key = UUID.randomUUID().toString();

        when(postService.existsPost(1)).thenReturn(post);
        when(amazonS3Service.uploadFile(file)).thenReturn(key);
        when(resourceRepository.save(any(Resource.class))).thenAnswer(i -> i.getArguments()[0]);

        List<ResourceDto> result = resourceServiceImpl.create(1, 1, List.of(file));
        assertEquals(key, result.get(0).getKey());
    }

    @Test
    void successDownloadResource() {
        S3ObjectInputStream inputStreamMock = mock(S3ObjectInputStream.class);

        when(amazonS3Service.downloadFile(anyString())).thenReturn(inputStreamMock);

        InputStream result = resourceServiceImpl.downloadResource(anyString());

        assertEquals(inputStreamMock, result);
    }

    @Test
    void successDeleteFile() {
        String key = "test";
        Post post = Post.builder()
                .id(1)
                .authorId(1)
                .build();
        Resource resource = Resource.builder()
                .id(1)
                .post(post)
                .build();

        when(resourceRepository.findByKey(key)).thenReturn(resource);

        resourceServiceImpl.deleteFile(key, 1);

        verify(resourceValidator, times(1)).validateExistenceByKey(key);
        verify(resourceRepository, times(1)).deleteByKey(key);
        verify(amazonS3Service, times(1)).deleteFile(key);
    }
}