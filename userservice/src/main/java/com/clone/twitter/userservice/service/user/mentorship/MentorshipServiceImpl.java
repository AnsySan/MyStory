package com.clone.twitter.userservice.service.user.mentorship;

import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.goal.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService {

    private final GoalRepository goalRepository;

    @Override
    @Transactional
    public void deleteMentorFromMentee(User mentor) {
        List<User> mentees = mentor.getMentees();
        mentees.forEach(mentee -> {
            mentee.getMentors().remove(mentor);
            mentee.getSetGoals().stream()
                    .filter(goal -> goal.getMentor().equals(mentor))
                    .forEach(goal -> {
                        goal.setMentor(mentee);
                        goalRepository.save(goal);
                    });
        });
    }
}