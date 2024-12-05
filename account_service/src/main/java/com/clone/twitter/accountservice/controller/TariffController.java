package com.clone.twitter.accountservice.controller;

import com.clone.twitter.accountservice.dto.TariffDto;
import com.clone.twitter.accountservice.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/savings-account/tariff")
public class TariffController {
    private final TariffService tariffService;

    @PostMapping
    public ResponseEntity<TariffDto> addTariff(@RequestBody @Valid TariffDto tariffDto) {
        var tariff = tariffService.createTariff(tariffDto);
        return ResponseEntity.ok().body(tariff);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffDto> getTariff(@PathVariable long id) {
        var tariff = tariffService.getTariffDtoById(id);
        return ResponseEntity.ok().body(tariff);
    }

    @PutMapping
    public ResponseEntity<TariffDto> updateTariff(@RequestBody @Valid TariffDto tariffDto) {
        var tariff = tariffService.updateTariff(tariffDto);
        return ResponseEntity.ok().body(tariff);
    }
}