package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.exception.NotFoundException;
import com.tretsoft.spa.exception.TooManyValuesException;
import com.tretsoft.spa.model.cash.CashProduct;
import com.tretsoft.spa.repository.cash.CashProductRepository;
import com.tretsoft.spa.service.CrudService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CashProduct getByName(String name) {
        if (name == null || name.isEmpty())
            throw new NotFoundException("Empty product name");

        List<CashProduct> cashProductList = cashProductRepository.findByNameIgnoreCaseAndUser(name, authenticationService.getCurrentUser());
        if (cashProductList.size() > 1)
            throw new TooManyValuesException("Product name = '" + name + "'");
        if (cashProductList.size() != 1)
            throw new NotFoundException("Product name = '" + name + "'");

        return cashProductList.get(0);
    }

    /*
    find by id
    OR find by name
    OR create
    */
    public CashProduct findOrCreate(CashProduct product) {
        if (product == null)
            return null;

        if (product.getId() != null)
            return getById(product.getId());

        try {
            return getByName(product.getName());
        } catch (Exception ex) {
            return create(product);
        }
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
