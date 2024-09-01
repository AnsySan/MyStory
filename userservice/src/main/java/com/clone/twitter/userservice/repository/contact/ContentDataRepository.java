package com.clone.twitter.userservice.repository.contact;

import com.clone.twitter.userservice.model.ContentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentDataRepository extends JpaRepository<ContentData, Long> {
}