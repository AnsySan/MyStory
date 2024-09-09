package com.clone.twitter.postservice.service.resource;

import com.clone.twitter.postservice.dto.ResourceDto;
import com.clone.twitter.postservice.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ResourceService {

    Resource findById(Long id);

    List<ResourceDto> create(Long postId, Long userId, List<MultipartFile> files);

    InputStream downloadResource(String key);

    void deleteFile(String key, Long userId);
}