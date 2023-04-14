package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.repository.cash.CashWalletRepository;
import com.tretsoft.spa.service.CrudService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CashWalletService implements CrudService<CashWallet> {

    private final CashWalletRepository cashWalletRepository;

    private final AuthenticationService authenticationService;

    private final CurrencyService currencyService;

    @Override
    public List<CashWallet> getAll() {
        return cashWalletRepository.findByUser(authenticationService.getCurrentUser());
    }

    @Override
    public CashWallet getById(Long id) {
        return findAndCheckAccess(id);
    }

    @Override
    public CashWallet create(CashWallet obj) {
        if (obj.getHidden() == null)
            obj.setHidden(false);

        if (obj.getCurrency() != null && obj.getCurrency().getId() == null) {
            obj.setCurrency(currencyService.getByName(obj.getCurrency().getName()));
        }

        obj.setUser(authenticationService.getCurrentUser());

        return cashWalletRepository.save(obj);
    }

    @Override
    public CashWallet update(CashWallet obj) {

        CashWallet updatedWallet = findAndCheckAccess(obj.getId());

        if (obj.getCurrency() == null) {
            throw new BadRequestException("Currency attribute is empty");
        }
        Currency currency = ((obj.getCurrency().getId() != null)
                ? obj.getCurrency()
                : currencyService.getByName(obj.getCurrency().getName()));

        updatedWallet.setCurrency(currency);
        updatedWallet.setHidden(obj.getHidden());
        updatedWallet.setName(obj.getName());

        return cashWalletRepository.save(updatedWallet);
    }

    @Override
    public void delete(Long id) {
        CashWallet wallet = findAndCheckAccess(id);
        cashWalletRepository.delete(wallet);
    }

    private CashWallet findAndCheckAccess(Long id) {
        CashWallet label = cashWalletRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Wallet id=" + id + " not found"));

        if (!label.getUser().equals(authenticationService.getCurrentUser()))
            throw new ForbiddenException("Wallet id=" + label.getId());

        return label;
    }
}
