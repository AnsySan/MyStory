package com.clone.twitter.userservice.service.user;

import java.util.List;

public interface UserBannerService {

    public void banUserById(Long userId);

    public void banUsersByIds(List<Long> userIds);
}
