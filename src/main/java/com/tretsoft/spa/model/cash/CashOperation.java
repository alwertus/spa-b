package com.tretsoft.spa.model.cash;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_operation")
public class CashOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "wallet_cell_src_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell walletCellSource;

    @JoinColumn(name = "wallet_cell_dst_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell walletCellDestination;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashProduct product;

    private Calendar created;

    private Calendar completeDate;

    @Column(nullable = false)
    private Float sum;

    private Float rate;

    private Float transferFee;

    private String compositeSum;

    private String notes;

    private Boolean isAutofill;

}
