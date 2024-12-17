package com.clone.twitter.userservice.repository.goal;

import com.clone.twitter.userservice.model.goal.GoalInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalInvitationRepository extends JpaRepository<GoalInvitation, Long> {
}
