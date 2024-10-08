package com.clone.twitter.notificationservice.repository;

import com.clone.twitter.notificationservice.entity.TelegramProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramProfilesRepository extends JpaRepository<TelegramProfile, Long> {

    Optional<TelegramProfile> findByUserId(long userId);

    Optional<TelegramProfile> findByChatId(long chatId);

    boolean existsByChatId(long chatId);
}