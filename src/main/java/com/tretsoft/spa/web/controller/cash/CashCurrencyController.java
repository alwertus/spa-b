package com.tretsoft.spa.web.controller.cash;

import com.tretsoft.spa.service.cash.CurrencyService;
import com.tretsoft.spa.web.dto.cash.CurrencyDto;
import com.tretsoft.spa.web.mapper.cash.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cash-currency")
@RequiredArgsConstructor
public class CashCurrencyController {

    private final CurrencyMapper currencyMapper;

    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDto> getAll() {
        return currencyMapper.sourcesToDtos(currencyService.getAll());
    }
}
