package com.clone.twitter.userservice.dto.subscription;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriptionUserFilterDto {
    private String namePattern;
    private String aboutPattern;
    private String emailPattern;
    private String contactPattern;
    private String countryPattern;
    private String cityPattern;
    private String phonePattern;
    private int page;
    private int pageSize;
}