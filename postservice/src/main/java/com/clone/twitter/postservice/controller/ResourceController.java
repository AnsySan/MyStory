package com.clone.twitter.postservice.controller;

import com.clone.twitter.postservice.context.UserContext;
import com.clone.twitter.postservice.dto.ResourceDto;
import com.clone.twitter.postservice.service.resource.ResourceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceServiceImpl resourceService;
    private final UserContext userContext;

    @Operation(summary = "Save resource")
    @PostMapping("upload/{postId}")
    public List<ResourceDto> uploadFiles(
            @PathVariable long postId,
            @RequestPart List<MultipartFile> files
    ) {
        return resourceService.create(postId, userContext.getUserId(), files);
    }

    @Operation(summary = "Download resource")
    @GetMapping("download/{key}")
    public InputStream downloadFile(@PathVariable String key) {
        return resourceService.downloadResource(key);
    }

    @Operation(summary = "Delete resource")
    @DeleteMapping("{key}")
    public void deleteFile(@PathVariable String key) {
        resourceService.deleteFile(key, userContext.getUserId());
    }
}