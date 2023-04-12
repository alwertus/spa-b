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
@Table(name = "cash_wallet")
public class CashWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private SpaUser user;

    @JoinColumn(name = "currency_id", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH)
    private Currency currency;

    @OneToMany(mappedBy = "wallet", cascade= CascadeType.ALL)
    private List<CashWalletCell> cells;

    private String name;

    @Column(columnDefinition = "boolean default false not null")
    private Boolean hidden;

}
