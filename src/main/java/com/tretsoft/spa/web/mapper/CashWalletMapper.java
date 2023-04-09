package com.tretsoft.spa.web.mapper;

import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.web.dto.CashWalletDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashWalletMapper extends BaseMapper<CashWallet, CashWalletDto> {
}
