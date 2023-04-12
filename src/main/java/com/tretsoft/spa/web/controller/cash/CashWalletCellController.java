package com.tretsoft.spa.web.controller.cash;

import com.tretsoft.spa.model.cash.CashWalletCell;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashWalletCellService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.cash.CashWalletCellDto;
import com.tretsoft.spa.web.mapper.cash.CashWalletCellMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash-wallet-cell")
public class CashWalletCellController extends CrudController<CashWalletCell, CashWalletCellDto> {

    public CashWalletCellController(CashWalletCellService service, CashWalletCellMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }

}
