package com.tretsoft.spa.service.update.handler;

import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.repository.cash.CurrencyRepository;
import com.tretsoft.spa.service.auth.RoleService;
import com.tretsoft.spa.service.auth.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HandlerVersion0 extends UpdateHandler {

    private final RoleService roleService;

    private final UserService userService;

    private final CurrencyRepository currencyRepository;

    public HandlerVersion0(RoleService roleService, UserService userService, CurrencyRepository currencyRepository) {
        super("0", "1");
        this.roleService = roleService;
        this.userService = userService;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run() {
        SpaRole newRole = roleService.createRole("PAGE_CASH", true, 2);

        currencyRepository.saveAll(List.of(
                Currency.builder().name("USD").build(),
                Currency.builder().name("RUB").build(),
                Currency.builder().name("KGS").build()
        ));

        userService.getAllUsers()
                .forEach(user -> userService.addRoleToUser(user, newRole));
    }

}
