package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.repository.CashWalletRepository;
import com.tretsoft.spa.service.CurdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CashWalletService implements CurdService<CashWallet> {

    private final CashWalletRepository cashWalletRepository;

    @Override
    public List<CashWallet> getAll() {
        return cashWalletRepository.findAll();
    }

    @Override
    public CashWallet create(CashWallet obj) {
        if (obj.getHidden() == null)
            obj.setHidden(false);
        return cashWalletRepository.save(obj);
    }

    @Override
    public CashWallet update(CashWallet obj) {
        return null;
    }

    @Override
    public void delete(Long id) {
        cashWalletRepository.deleteById(id);
    }
}
