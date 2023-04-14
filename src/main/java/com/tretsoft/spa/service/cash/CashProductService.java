package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.model.cash.CashProduct;
import com.tretsoft.spa.repository.cash.CashProductRepository;
import com.tretsoft.spa.service.CrudService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CashProductService implements CrudService<CashProduct> {

    private final AuthenticationService authenticationService;

    private final CashWalletCellService cashWalletCellService;

    private final CashProductRepository cashProductRepository;

    @Override
    public List<CashProduct> getAll() {
        return cashProductRepository.findByUser(authenticationService.getCurrentUser());
    }

    @Override
    public CashProduct getById(Long id) {
        return findAndCheckAccess(id);
    }

    @Override
    public CashProduct create(CashProduct obj) {
        obj.setUser(authenticationService.getCurrentUser());

        return cashProductRepository.save(obj);
    }

    @Override
    public CashProduct update(CashProduct obj) {
        CashProduct updated = findAndCheckAccess(obj.getId());

        updated.setName(obj.getName());
        updated.setGrade(obj.getGrade());

        if (obj.getDefaultCashWalletCellSource() != null) {
            cashWalletCellService.getById(obj.getDefaultCashWalletCellSource().getId());
        }
        if (obj.getDefaultCashWalletCellDestination() != null) {
            cashWalletCellService.getById(obj.getDefaultCashWalletCellDestination().getId());
        }
        updated.setDefaultCashWalletCellSource(obj.getDefaultCashWalletCellSource());
        updated.setDefaultCashWalletCellDestination(obj.getDefaultCashWalletCellDestination());

        return cashProductRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        CashProduct cashProduct = findAndCheckAccess(id);
        cashProductRepository.delete(cashProduct);
    }

    private CashProduct findAndCheckAccess(Long id) {
        CashProduct obj = cashProductRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Product id=" + id + " not found"));

        if (!obj.getUser().equals(authenticationService.getCurrentUser()))
            throw new ForbiddenException("Product id=" + obj.getId());

        return obj;
    }

}
