package com.clone.twitter.accountservice.mapper;

import com.clone.twitter.accountservice.dto.BalanceDto;
import com.clone.twitter.accountservice.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper {

    Balance toEntity(BalanceDto balanceDto);

    @Mapping(target = "accountId", source = "account.id")
    BalanceDto toDto(Balance balance);
}