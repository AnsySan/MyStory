package com.clone.twitter.user_service.repository.goal;

import com.clone.twitter.user_service.model.goal.GoalInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalInvitationRepository extends JpaRepository<GoalInvitation, Long> {
}
