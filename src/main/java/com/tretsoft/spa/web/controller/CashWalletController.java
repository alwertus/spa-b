package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.model.cash.CashWallet;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashWalletService;
import com.tretsoft.spa.web.dto.CashWalletDto;
import com.tretsoft.spa.web.mapper.CashWalletMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash-wallet")
public class CashWalletController extends CrudController<CashWallet, CashWalletDto> {

    public CashWalletController(CashWalletService service, CashWalletMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }

}
