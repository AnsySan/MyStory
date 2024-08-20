package com.clone.twitter.postservice.service;

import com.clone.twitter.postservice.entity.Post;
import com.clone.twitter.postservice.publisher.UserBannerPublisher;
import com.clone.twitter.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserBannerService {
    private final PostRepository postRepository;
    private final UserBannerPublisher userBannerPublisher;
    @Value("${limits.unverified_post_limit}")
    private int UNVERIFIED_POST_LIMIT;

    @Async("executorService")
    @Transactional
    public void banPosts() {
        List<Post> unverifiedPosts = postRepository.findAllByVerified(false);
        Map<Integer, Long> unverifiedPostsByUsers = unverifiedPosts.stream()
                .collect(Collectors.groupingBy((Post::getAuthorId), Collectors.counting()));
        List<Integer> userIdsToBan = unverifiedPostsByUsers.entrySet().stream()
                .filter((entry) -> entry.getValue() >= UNVERIFIED_POST_LIMIT)
                .map(Map.Entry::getKey)
                .toList();

        if (!userIdsToBan.isEmpty()) {
            userBannerPublisher.publish(userIdsToBan);
            postRepository.deleteAllByAuthorIdIn(userIdsToBan);
            log.info("Event Published!");
        }
    }
}