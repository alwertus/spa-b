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

    @JoinColumn(name = "wallet_id", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH)
    private CashWallet wallet;

    @Column(nullable = false)
    private String name;

    private Boolean hidden;

    private String icon;

    private String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashWalletCell that = (CashWalletCell) o;

        if (!wallet.equals(that.wallet)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = wallet.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
