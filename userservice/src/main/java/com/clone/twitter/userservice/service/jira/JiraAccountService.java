package com.clone.twitter.userservice.service.jira;

import com.clone.twitter.userservice.dto.jira.JiraAccountDto;

public interface JiraAccountService {
    JiraAccountDto addJiraAccount(long userId, JiraAccountDto jiraAccountDto);

    JiraAccountDto getJiraAccountInfo(long userId);
}