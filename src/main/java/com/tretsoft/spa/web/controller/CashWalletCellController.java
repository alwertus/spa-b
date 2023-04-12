package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.model.cash.CashWalletCell;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashWalletCellService;
import com.tretsoft.spa.web.dto.CashWalletCellDto;
import com.tretsoft.spa.web.mapper.CashWalletCellMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash-wallet-cell")
public class CashWalletCellController extends CrudController<CashWalletCell, CashWalletCellDto> {

    public CashWalletCellController(CashWalletCellService service, CashWalletCellMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }

}
