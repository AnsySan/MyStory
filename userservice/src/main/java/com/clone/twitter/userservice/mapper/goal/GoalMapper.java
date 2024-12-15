package com.clone.twitter.userservice.mapper.goal;

import com.clone.twitter.userservice.dto.goal.GoalDto;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.model.goal.Goal;
import com.clone.twitter.userservice.model.skill.Skill;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.goal.GoalRepository;
import com.clone.twitter.userservice.repository.skill.SkillRepository;
import com.clone.twitter.userservice.repository.user.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GoalMapper {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected GoalRepository goalRepository;
    @Autowired
    protected SkillRepository skillRepository;

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "mentor.id", target = "mentorId")
    @Mapping(source = "users", target = "userIds", qualifiedByName = "mapUsersToIdList")
    @Mapping(source = "skillsToAchieve", target = "skillIds", qualifiedByName = "mapSkillsToIdList")
    public abstract GoalDto toDto(Goal goal);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "mentor", ignore = true)
    @Mapping(target = "skillsToAchieve", ignore = true)
    public abstract Goal toEntity(GoalDto goalDto);

    public abstract void update(GoalDto goalDto, @MappingTarget Goal goal);

    @Named("mapSkillsToIdList")
    protected List<Long> mapSkillsToIdList(List<Skill> skillsToAchieve) {
        if (skillsToAchieve == null)
            return Collections.emptyList();
        return skillsToAchieve.stream().map(Skill::getId).toList();
    }

    @Named("mapUsersToIdList")
    protected List<Long> mapUsersToIdList(List<User> users) {
        if (users == null) return Collections.emptyList();
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    @Transactional
    public void convertDtoIdsToEntity(GoalDto goalDto, Goal goal) {
        if (goalDto.getMentorId() != null) {
            User mentor = getEntityById(goalDto.getMentorId(), userRepository, "Mentor");
            goal.setMentor(mentor);
        }

        if (goalDto.getParentId() != null) {
            Goal goalParent = getEntityById(goalDto.getParentId(), goalRepository, "Goal-parent");
            goal.setParent(goalParent);
        }

        convertSkillIds(goalDto, goal);
    }

    @Transactional
    public <T> T getEntityById(Long id, CrudRepository<T, Long> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(entityName + " with id: " + id + " was not found!"));
    }

    @Transactional
    public void convertSkillIds(GoalDto goalDto, Goal goal) {
        if (goalDto.getSkillIds() != null) {
            List<Skill> skills = new ArrayList<>();
            goalDto.getSkillIds().forEach(skillId -> {
                Skill skill = getEntityById(skillId, skillRepository, "Skill");
                skills.add(skill);
            });
            goal.setSkillsToAchieve(skills);
        }
    }

}