package com.tretsoft.spa.model.cash;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_product")
public class CashProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer grade;

    @JoinColumn(name = "default_wallet_cell_src_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell defaultCashWalletCellSource;

    @JoinColumn(name = "default_wallet_cell_dst_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell defaultCashWalletCellDestination;

}
