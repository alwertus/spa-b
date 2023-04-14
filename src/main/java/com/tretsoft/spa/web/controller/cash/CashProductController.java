package com.tretsoft.spa.web.controller.cash;

import com.tretsoft.spa.model.cash.CashProduct;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.cash.CashProductService;
import com.tretsoft.spa.web.controller.CrudController;
import com.tretsoft.spa.web.dto.cash.CashProductDto;
import com.tretsoft.spa.web.mapper.cash.CashProductMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash-product")
public class CashProductController extends CrudController<CashProduct, CashProductDto> {
    public CashProductController(CashProductService service, CashProductMapper mapper, AuthenticationService authenticationService) {
        super(service, mapper, authenticationService);
    }
}
