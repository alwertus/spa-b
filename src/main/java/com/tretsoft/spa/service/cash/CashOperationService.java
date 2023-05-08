package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.NotFoundException;
import com.tretsoft.spa.exception.NullAttributeException;
import com.tretsoft.spa.model.cash.CashOperation;
import com.tretsoft.spa.repository.cash.CashOperationRepository;
import com.tretsoft.spa.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashOperationService implements CrudService<CashOperation> {

    private final CashOperationRepository cashOperationRepository;

    private final CashWalletCellService cashWalletCellService;

    private final CashProductService cashProductService;

    @Override
    public List<CashOperation> getAll() {
        return cashOperationRepository.findAll();
    }

    public List<CashOperation> getAllByInterval(Calendar start, Calendar end) {
        return null;
    }

    @Override
    public CashOperation getById(Long id) {
        return null;
    }

    public float calcSum(String compositeSum) {
        if (compositeSum == null)
            return 0;

        return (float) Arrays.stream(compositeSum
                        .replaceAll("-", "+-")
                        .split("\\+"))
                .filter(o -> o != null && o.length() > 0)
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    @Override
    public CashOperation create(CashOperation obj) {
        // check user access
        if (obj.getWalletCellSource() != null) {
            obj.setWalletCellSource(cashWalletCellService.getById(obj.getWalletCellSource().getId()));
        }
        if (obj.getWalletCellDestination() != null) {
            obj.setWalletCellDestination(cashWalletCellService.getById(obj.getWalletCellDestination().getId()));
        }

        // set product
        if (obj.getProduct() != null) {
            obj.setProduct(cashProductService.findOrCreate(obj.getProduct()));
        }

        // not null
        if (obj.getSum() == null && obj.getCompositeSum() == null) {
            throw new NullAttributeException(new String[] {"sum", "compositeSum"});
        }
        if (obj.getWalletCellSource() == null && obj.getWalletCellDestination() == null) {
            throw new NullAttributeException(new String[] {"walletCellSource", "walletCellDestination"});
        }

        // autofill
        if (obj.getRate() == null) {
            obj.setRate(1.0f);
        }
        if (obj.getTransferFee() == null) {
            obj.setTransferFee(0.0f);
        }
        if (obj.getIsAutofill() == null) {
            obj.setIsAutofill(false);
        }

        // calc composite sum
        if (obj.getCompositeSum() != null) {
            obj.setSum(calcSum(obj.getCompositeSum()));
        }

        return cashOperationRepository.save(obj);
    }

    @Override
    public CashOperation update(CashOperation obj) {
        CashOperation updated = cashOperationRepository
                .findById(obj.getId())
                .orElseThrow(() -> new NotFoundException("Operation id='" + obj.getId() + "'"));

        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
