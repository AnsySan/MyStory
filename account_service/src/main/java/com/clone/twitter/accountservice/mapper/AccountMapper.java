package com.clone.twitter.accountservice.mapper;

import com.clone.twitter.accountservice.dto.SavingsAccountDto;
import com.clone.twitter.accountservice.dto.TariffDto;
import com.clone.twitter.accountservice.dto.account.AccountDto;
import com.clone.twitter.accountservice.model.Account;
import com.clone.twitter.accountservice.model.SavingAccount;
import com.clone.twitter.accountservice.model.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountDto toDto(Account account);

    @Mapping(target = "status", expression = "java(com.clone.twitter.accountservice.model.AccountStatus.ACTIVE)")
    Account toEntity(AccountDto accountDto);
    @Mapping(source = "currentTariff", target = "currentTariff", ignore = true)
    SavingAccount toEntity(SavingsAccountDto dto);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "currentTariff", source = "currentTariff.id")
    SavingsAccountDto toDto(SavingAccount entity);

    TariffDto toDto(Tariff entity);

    Tariff toEntity(TariffDto dto);
}