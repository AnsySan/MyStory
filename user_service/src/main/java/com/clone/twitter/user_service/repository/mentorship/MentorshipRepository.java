package com.clone.twitter.user_service.repository.mentorship;

import com.clone.twitter.user_service.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorshipRepository extends CrudRepository<User, Long> {
}