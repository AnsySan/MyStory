package com.clone.twitter.userservice.filter.user;

import com.clone.twitter.userservice.dto.user.UserFilterDto;
import com.clone.twitter.userservice.model.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Component
public class UserSkillFilter implements UserFilter {

    @Override
    public boolean isAcceptable(UserFilterDto userFilterDto) {
        return userFilterDto.getSkillPattern() != null;
    }

    @Override
    @Transactional
    public Stream<User> applyFilter(Stream<User> users, UserFilterDto userFilterDto) {
        return users.filter(user -> user.getSkills().stream()
                .anyMatch(skill -> skill.getTitle().startsWith(userFilterDto.getSkillPattern())));
    }
}