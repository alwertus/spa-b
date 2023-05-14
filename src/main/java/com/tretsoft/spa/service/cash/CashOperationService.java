package com.tretsoft.spa.service.cash;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.NotFoundException;
import com.tretsoft.spa.exception.NullAttributeException;
import com.tretsoft.spa.helpers.ObjectOperations;
import com.tretsoft.spa.model.cash.CashOperation;
import com.tretsoft.spa.repository.cash.CashOperationRepository;
import com.tretsoft.spa.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Log4j2
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

    private void checks(CashOperation obj) {
        // non null
        if (obj.getSum() == null && obj.getCompositeSum() == null) {
            throw new NullAttributeException(new String[] {"sum", "compositeSum"});
        }
        if (obj.getWalletCellSource() == null && obj.getWalletCellDestination() == null) {
            throw new NullAttributeException(new String[] {"walletCellSource", "walletCellDestination"});
        }

        // equals
        if (obj.getWalletCellSource() != null && obj.getWalletCellDestination() != null && Objects.equals(obj.getWalletCellSource().getId(), obj.getWalletCellDestination().getId())) {
            throw new BadRequestException("[walletCellSource] equals [walletCellDestination]");
        }
    }

    @Override
    public CashOperation getById(Long id) {
        return cashOperationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Operation id='" + id + "'"));
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

    private void findWalletCellAndCheckAccess(CashOperation obj) {
        if (obj.getWalletCellSource() != null) {
            obj.setWalletCellSource(cashWalletCellService.getById(obj.getWalletCellSource().getId()));
        }
        if (obj.getWalletCellDestination() != null) {
            obj.setWalletCellDestination(cashWalletCellService.getById(obj.getWalletCellDestination().getId()));
        }
    }

    @Override
    public CashOperation create(CashOperation obj) {
        // check user access
        findWalletCellAndCheckAccess(obj);

        // set product
        if (obj.getProduct() != null) {
            obj.setProduct(cashProductService.findOrCreate(obj.getProduct()));
        }

        checks(obj);

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
    @Transactional
    public CashOperation update(CashOperation obj) {
        CashOperation updated = cashOperationRepository
                .findById(obj.getId())
                .orElseThrow(() -> new NotFoundException("Operation id='" + obj.getId() + "'"));

        checks(obj);

        if (obj.getProduct() != null &&
                (obj.getProduct().getId() == null ||
                        updated.getProduct().getId() == null ||
                        !obj.getProduct().getId().equals(updated.getProduct().getId()))
        ) {
            updated.setProduct(cashProductService.findOrCreate(obj.getProduct()));
        }

        // check user access
        findWalletCellAndCheckAccess(obj);

        ObjectOperations.copyNonNullFields(obj, updated, List.of("id", "product", "sum", "compositeSum", "walletCellSource", "walletCellDestination"));

        updated.setWalletCellSource(obj.getWalletCellSource());
        updated.setWalletCellDestination(obj.getWalletCellDestination());

        // if sum changed and not null -> compositeSum=null
        if (obj.getSum() != null && !Objects.equals(obj.getSum(), updated.getSum())) {
            updated.setSum(obj.getSum());
            obj.setCompositeSum(null);
            updated.setCompositeSum(null);
        }

        // if compositeSum changed and not null -> sum=recalculate
        if (obj.getCompositeSum() != null && !Objects.equals(obj.getCompositeSum(), updated.getCompositeSum())) {
            updated.setSum(calcSum(obj.getCompositeSum()));
            updated.setCompositeSum(obj.getCompositeSum());
        }

        return cashOperationRepository.save(updated);
    }

    @Override
    public void delete(Long id) {
        CashOperation deleted = cashOperationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Operation id='" + id + "'"));

        findWalletCellAndCheckAccess(deleted);

        cashOperationRepository.delete(deleted);
    }
}
