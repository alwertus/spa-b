package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.cash.CashWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashWalletRepository extends JpaRepository<CashWallet, Long> {
}
