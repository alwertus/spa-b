package com.tretsoft.spa.web.mapper.cash;

import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.web.dto.cash.CashWalletDto;
import com.tretsoft.spa.web.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashWalletMapper extends BaseMapper<CashWallet, CashWalletDto> {

}
