package com.tretsoft.spa.repository.cash;

import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.model.cash.CashWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashWalletRepository extends JpaRepository<CashWallet, Long> {

    List<CashWallet> findByUser(SpaUser user);

}
