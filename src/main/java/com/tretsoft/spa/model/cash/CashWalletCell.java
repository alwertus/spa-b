package com.tretsoft.spa.model.cash;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_wallet_cell")
public class CashWalletCell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "wallet_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWallet wallet;

    private String name;

    private Boolean hidden;

    private String icon;

    private String notes;

}
