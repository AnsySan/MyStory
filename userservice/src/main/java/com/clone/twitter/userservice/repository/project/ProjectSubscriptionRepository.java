package com.clone.twitter.userservice.repository.project;

import com.clone.twitter.userservice.model.ProjectSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSubscriptionRepository extends CrudRepository<ProjectSubscription, Long> {
}