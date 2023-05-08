package com.tretsoft.spa.repository.cash;

import com.tretsoft.spa.model.cash.CashProduct;
import com.tretsoft.spa.model.user.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashProductRepository extends JpaRepository<CashProduct, Long> {

    List<CashProduct> findByUser(SpaUser user);

    List<CashProduct> findByNameIgnoreCaseAndUser(String name, SpaUser user);

}
