package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.cash.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> getByName(String name);

}
