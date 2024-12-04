package com.clone.twitter.accountservice.service.balance;

import com.clone.twitter.accountservice.dto.BalanceDto;
import com.clone.twitter.accountservice.dto.BalanceUpdateDto;
import com.clone.twitter.accountservice.dto.BalanceUpdateType;
import com.clone.twitter.accountservice.mapper.BalanceMapperImpl;
import com.clone.twitter.accountservice.model.Balance;
import com.clone.twitter.accountservice.repository.BalanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    BalanceRepository balanceRepository;

    @Spy
    List<BalanceUpdater> updaters;

    @Spy
    BalanceMapperImpl balanceMapper;

    @Mock
    BalanceHistoryService balanceHistoryService;

    @InjectMocks
    BalanceService balanceService;

    @Test
    @DisplayName("Update test. Case REPLENISHMENT")
    void updateTestReplenishment() {
        BalanceUpdateDto dto = BalanceUpdateDto.builder()
                .balanceId(1L)
                .type(BalanceUpdateType.REPLENISHMENT)
                .amount(new BigDecimal(100))
                .build();
        Balance balance = Balance.builder()
                .id(1L)
                .actualBalance(new BigDecimal(100))
                .authBalance(new BigDecimal(100))
                .build();
        BalanceDto expectedBalance = BalanceDto.builder()
                .id(1L)
                .actualBalance(new BigDecimal(200))
                .authBalance(new BigDecimal(200))
                .build();
        BalanceReplenishment updater = new BalanceReplenishment();

        when(balanceRepository.findById(1L))
                .thenReturn(Optional.of(balance));
        when(updaters.stream())
                .thenReturn(Stream.of(updater));
        when(balanceRepository.save(balance))
                .thenReturn(balance);

        BalanceDto balanceDto = balanceService.update(dto);

        verify(balanceRepository, times(1)).save(balance);

        assertEquals(expectedBalance, balanceDto);
    }

    @Test
    @DisplayName("Update test. Case AUTHORIZATION")
    void updateTestAuthorization() {
        BalanceUpdateDto dto = BalanceUpdateDto.builder()
                .balanceId(1L)
                .type(BalanceUpdateType.AUTHORIZATION)
                .amount(new BigDecimal(100))
                .build();
        Balance balance = Balance.builder()
                .id(1L)
                .actualBalance(new BigDecimal(100))
                .authBalance(new BigDecimal(100))
                .build();
        BalanceDto expectedBalance = BalanceDto.builder()
                .id(1L)
                .actualBalance(new BigDecimal(100))
                .authBalance(new BigDecimal(0))
                .build();
        BalanceAuthorization updater = new BalanceAuthorization();

        when(balanceRepository.findById(1L))
                .thenReturn(Optional.of(balance));
        when(updaters.stream())
                .thenReturn(Stream.of(updater));
        when(balanceRepository.save(balance))
                .thenReturn(balance);

        BalanceDto balanceDto = balanceService.update(dto);

        verify(balanceRepository, times(1)).save(balance);

        assertEquals(expectedBalance, balanceDto);
    }

    @Test
    @DisplayName("Update test. Case CLEAR")
    void updateTestClear() {
        BalanceUpdateDto dto = BalanceUpdateDto.builder()
                .balanceId(1L)
                .type(BalanceUpdateType.CLEARING)
                .amount(new BigDecimal(100))
                .build();
        Balance balance = Balance.builder()
                .id(1L)
                .actualBalance(new BigDecimal(100))
                .authBalance(new BigDecimal(100))
                .build();
        BalanceDto expectedBalance = BalanceDto.builder()
                .id(1L)
                .actualBalance(new BigDecimal(0))
                .authBalance(new BigDecimal(100))
                .build();
        BalanceClearing updater = new BalanceClearing();

        when(balanceRepository.findById(1L))
                .thenReturn(Optional.of(balance));
        when(updaters.stream())
                .thenReturn(Stream.of(updater));
        when(balanceRepository.save(balance))
                .thenReturn(balance);

        BalanceDto balanceDto = balanceService.update(dto);

        verify(balanceRepository, times(1)).save(balance);

        assertEquals(expectedBalance, balanceDto);
    }
}