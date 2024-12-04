package com.clone.twitter.accountservice.mapper;

import com.clone.twitter.accountservice.dto.BalanceHistoryDto;
import com.clone.twitter.accountservice.model.BalanceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceHistoryMapper {

    @Mapping(target = "balance", ignore = true)
    BalanceHistory toEntity(BalanceHistoryDto balanceHistoryDto);

    @Mapping(target = "balanceId", source = "balance.id")
    BalanceHistoryDto toDto(BalanceHistory balanceHistory);
}