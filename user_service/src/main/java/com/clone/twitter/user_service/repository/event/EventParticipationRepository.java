package com.clone.twitter.user_service.repository.event;

import com.clone.twitter.user_service.model.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipationRepository extends CrudRepository<User, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_event (event_id, user_id) VALUES (:eventId, :userId")
    void register(long eventId, long userId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_event WHERE event_id = :eventId and user_id = :userId")
    void unregister(long eventId, long userId);

    @Query(nativeQuery = true, value = """
            SELECT u.* FROM user u
            JOIN user_event ue ON u.id = ue.user_id
            WHERE ue.event_id = :eventId
            """)
    List<User> findAllParticipantsByEventId(long eventId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(ue.id) FROM user_event ue
            WHERE ue.event_id = :eventId
            """)
    int countParticipants(long eventId);
}