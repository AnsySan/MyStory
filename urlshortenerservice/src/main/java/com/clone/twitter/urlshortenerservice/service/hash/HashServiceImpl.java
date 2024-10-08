package com.clone.twitter.urlshortenerservice.service.hash;

import com.clone.twitter.urlshortenerservice.entity.Hash;
import com.clone.twitter.urlshortenerservice.repository.HashRepository;
import com.clone.twitter.urlshortenerservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class HashServiceImpl implements HashService {

    private final UrlRepository urlRepository;
    private final HashRepository hashRepository;

    @Value("${app.scheduler.period}")
    private int period;
    private final LocalDateTime dateTime = LocalDateTime.now().minusDays(period);

    @Override
    @Transactional
    public void clean() {
        log.info("Clean hash urlRepository {}", LocalDateTime.now());
        List<Hash> updatingHashes = urlRepository.deleteOldUrl(dateTime).stream()
                .map(url -> new Hash(url.getHash()))
                .toList();

        hashRepository.saveAll(updatingHashes);
    }

}