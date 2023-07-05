package com.tretsoft.spa.service.update.handler;

import com.tretsoft.spa.model.cash.Currency;
import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.repository.cash.CurrencyRepository;
import com.tretsoft.spa.repository.user.RoleRepository;
import com.tretsoft.spa.service.auth.RoleService;
import com.tretsoft.spa.service.auth.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

// set priority to roles

@Component
public class HandlerVersion1 extends UpdateHandler {

    private final RoleRepository roleRepository;

    public HandlerVersion1(RoleService roleService, RoleRepository roleRepository) {
        super("1", "2");
        this.roleRepository = roleRepository;
    }

    @Override
    public void run() {
        List<SpaRole> roles = roleRepository.findAll();
        roles.forEach(e -> {
            if (e.getName().equals("OWNER"))
                e.setPriority(0);
            else if (e.getName().equals("ADMIN"))
                e.setPriority(1);
            else
                e.setPriority(2);
        });

        roleRepository.saveAll(roles);
    }

}
