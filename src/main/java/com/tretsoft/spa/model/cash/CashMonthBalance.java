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
@Table(name = "cash_month_balance")
public class CashMonthBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Calendar date;

    private Float sum;

    @JoinColumn(name = "wallet_cell_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell walletCell;

}
