package com.clone.twitter.user_service.repository.project;

import com.clone.twitter.user_service.model.ProjectSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSubscriptionRepository extends CrudRepository<ProjectSubscription, Long> {
}