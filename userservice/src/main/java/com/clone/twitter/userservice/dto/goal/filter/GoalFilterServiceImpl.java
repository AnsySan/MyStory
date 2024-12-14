package com.clone.twitter.userservice.dto.goal.filter;

import com.clone.twitter.userservice.dto.goal.GoalFilterDto;
import com.clone.twitter.userservice.filter.goal.GoalFilter;
import com.clone.twitter.userservice.model.goal.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GoalFilterServiceImpl implements GoalFilterService{

    private final List<GoalFilter> goalFilters;

    @Override
    public Stream<Goal> applyFilters(Stream<Goal> goals, GoalFilterDto goalFilterDto) {
        if (goalFilterDto != null) {
            for (GoalFilter goalFilter : goalFilters) {
                if (goalFilter.isAcceptable(goalFilterDto)) {
                    goals = goalFilter.applyFilter(goals, goalFilterDto);
                }
            }
        }

        return goals;
    }
}