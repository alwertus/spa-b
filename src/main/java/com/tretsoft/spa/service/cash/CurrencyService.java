package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.repository.cash.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    public Currency getByName(String currencyName) {
        return currencyRepository
                .getByName(currencyName)
                .orElseThrow(() -> new BadRequestException("Currency name=" + currencyName + " not found"));
    }
}
