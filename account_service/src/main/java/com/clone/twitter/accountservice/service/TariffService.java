package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.dto.TariffDto;
import com.clone.twitter.accountservice.mapper.AccountMapper;
import com.clone.twitter.accountservice.model.Tariff;
import com.clone.twitter.accountservice.repository.TariffRepository;
import com.clone.twitter.accountservice.util.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
    private final AccountMapper mapper;
    private final JsonMapper jsonMapper;

    public Tariff getTariffById(long id) {
        return tariffRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No tariff with id: " + id)
        );
    }

    public TariffDto getTariffDtoById(long id) {
        var tariff = tariffRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No tariff with id: " + id)
        );
        return mapper.toDto(tariff);
    }

    public TariffDto createTariff(TariffDto tariffDto) {
        var tariff = tariffRepository.save(mapper.toEntity(tariffDto));
        return mapper.toDto(tariffRepository.save(tariff));
    }

    @Transactional
    public TariffDto updateTariff(TariffDto tariffDto) {
        var tariff = tariffRepository.findById(tariffDto.getId()).orElseThrow(
                () -> new RuntimeException("No tariff with id: " + tariffDto.getId())
        );
        tariff.setTariffType(tariffDto.getTariffType());
        if (!tariffDto.getCurrentRate().equals(BigDecimal.valueOf(0)) && !tariffDto.getCurrentRate().equals(tariff.getCurrentRate())) {
            String history = tariff.getRateHistory();
            if (history == null) history = "[]";
            var historyList = jsonMapper.toObject(history, ArrayList.class).orElse(new ArrayList<BigDecimal>());
            historyList.add(tariff.getCurrentRate());
            var json = jsonMapper.toJson(historyList).orElse("[]");
            tariff.setRateHistory(json);
            tariff.setCurrentRate(tariffDto.getCurrentRate());
        }
        tariffRepository.save(tariff);
        return mapper.toDto(tariff);
    }
}