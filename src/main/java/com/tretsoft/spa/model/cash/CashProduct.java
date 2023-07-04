package com.tretsoft.spa.model.cash;

import com.tretsoft.spa.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_product")
public class CashProduct {
// TODO: create unique index name+user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer grade;

    @JoinColumn(name = "default_wallet_cell_src_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell defaultCashWalletCellSource;

    @JoinColumn(name = "default_wallet_cell_dst_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWalletCell defaultCashWalletCellDestination;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private SpaUser user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CashOperation> operations;
}
