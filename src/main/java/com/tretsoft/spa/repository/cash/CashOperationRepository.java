package com.tretsoft.spa.repository.cash;

import com.tretsoft.spa.model.cash.CashOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CashOperationRepository extends JpaRepository<CashOperation, Long> {

    @NonNull
    @Query("""
select t
from CashOperation t
where t.id = :id
""")
    Optional<CashOperation> findById(@NonNull @Param("id") Long id);

}
