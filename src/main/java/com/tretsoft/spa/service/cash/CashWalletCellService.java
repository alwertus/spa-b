package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.exception.MethodNotSupportedException;
import com.tretsoft.spa.model.cash.CashWalletCell;
import com.tretsoft.spa.repository.cash.CashWalletCellRepository;
import com.tretsoft.spa.service.CrudService;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CashWalletCellService implements CrudService<CashWalletCell> {

    private final CashWalletCellRepository cashWalletCellRepository;

    private final AuthenticationService authenticationService;

    private final CashWalletService cashWalletService;

    @Override
    public List<CashWalletCell> getAll() {
        throw new MethodNotSupportedException();
    }

    @Override
    public CashWalletCell getById(Long id) {
        return findAndCheckAccess(id);
    }

    @Override
    public CashWalletCell create(CashWalletCell obj) {
        if (obj.getHidden() == null)
            obj.setHidden(false);

        if (obj.getWallet().getId() == null) {
            throw new BadRequestException("WalletId is null");
        }

        obj.setWallet(cashWalletService.getById(obj.getWallet().getId()));

        return cashWalletCellRepository.save(obj);
    }

    @Override
    public CashWalletCell update(CashWalletCell obj) {
        CashWalletCell updated = findAndCheckAccess(obj.getId());

        cashWalletService.getById(updated.getWallet().getId()); // check access

        updated.setName(obj.getName());
        updated.setHidden(obj.getHidden());
        updated.setIcon(obj.getIcon());
        updated.setNotes(obj.getNotes());

        return cashWalletCellRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        CashWalletCell cell = findAndCheckAccess(id);
        cashWalletCellRepository.delete(cell);
    }

    private CashWalletCell findAndCheckAccess(Long id) {
        CashWalletCell walletCell = cashWalletCellRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("WalletCell id=" + id + " not found"));

        if (!walletCell.getWallet().getUser().equals(authenticationService.getCurrentUser()))
            throw new ForbiddenException("WalletCell id=" + walletCell.getId());

        return walletCell;
    }
}
