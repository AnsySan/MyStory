package com.clone.twitter.user_service.repository.user;

import com.clone.twitter.user_service.model.user.UserSkillGuarantee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSkillGuaranteeRepository extends CrudRepository<UserSkillGuarantee, Long> {
    @Query(nativeQuery = true, value = "INSERT INTO user_skill_guarantee (skill_id, user_id, guarantor_id) VALUES (:skillId, :userId, :guarantorId)")
    @Modifying
    void assignSkillGuaranteeToUser(long skillId, long userId, long guarantorId);
}