package com.tretsoft.spa.web.mapper;

import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.model.cash.CashWalletCell;
import com.tretsoft.spa.web.dto.CashWalletCellDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CashWalletCellMapper extends BaseMapper<CashWalletCell, CashWalletCellDto> {

    @Mapping(source = "walletId", target = "wallet", qualifiedByName = "idToWallet")
    CashWalletCell dtoToSource(CashWalletCellDto dto);

    @Named("idToWallet")
    static CashWallet idToWallet(Long value) {
        if (value == null) return null;
        return CashWallet
                .builder()
                .id(value)
                .build();
    }

    @Mapping(source = "wallet", target = "walletId", qualifiedByName = "walletToId")
    CashWalletCellDto sourceToDto(CashWalletCell domain);

    @Named("walletToId")
    static Long walletToId(CashWallet value) {
        return value == null ? null : value.getId();
    }



}
