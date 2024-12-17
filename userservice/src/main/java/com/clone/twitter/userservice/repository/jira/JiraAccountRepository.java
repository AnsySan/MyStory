package com.clone.twitter.userservice.repository.jira;

import com.clone.twitter.userservice.model.jira.JiraAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JiraAccountRepository extends JpaRepository<JiraAccount, Long> {
    Optional<JiraAccount> findByUserId(long userId);
}