package com.clone.twitter.user_service.repository.user;

import com.clone.twitter.user_service.model.user.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT COUNT(s.id) FROM users u
            JOIN user_skill us ON us.user_id = u.id
            JOIN skill s ON us.skill_id = s.id
            WHERE u.id = ?1 AND s.id IN (?2)
            """)
    int countOwnedSkills(long userId, List<Long> ids);

    @Query(nativeQuery = true, value = """
            SELECT u.* FROM users u
            JOIN user_premium up ON up.user_id = u.id
            WHERE up.end_date > NOW()
            """)
    Stream<User> findPremiumUsers();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) = 0 FROM users WHERE email = :valueToCheck")
    boolean isEmailUnique(@Param("valueToCheck") String valueToCheck);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) = 0 FROM users WHERE phone = :valueToCheck")
    boolean isPhoneUnique(@Param("valueToCheck") String valueToCheck);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) = 0 FROM users WHERE username = :valueToCheck")
    boolean isUsernameUnique(@Param("valueToCheck") String valueToCheck);
}