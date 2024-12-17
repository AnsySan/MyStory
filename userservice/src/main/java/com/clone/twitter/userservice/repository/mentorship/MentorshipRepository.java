package com.clone.twitter.userservice.repository.mentorship;

import com.clone.twitter.userservice.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorshipRepository extends CrudRepository<User, Long> {
}