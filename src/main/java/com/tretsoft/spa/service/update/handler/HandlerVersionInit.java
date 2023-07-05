package com.tretsoft.spa.service.update.handler;

import com.tretsoft.spa.service.auth.RoleService;
import org.springframework.stereotype.Component;

@Component
public class HandlerVersionInit extends UpdateHandler {

    private final RoleService roleService;

    public HandlerVersionInit(RoleService roleService) {
        super(null, "0");
        this.roleService = roleService;
    }

    private void initRoles() {
        roleService.createRole("OWNER", false, 0);
        roleService.createRole("ADMIN", false, 1);
        roleService.createRole("USER", true, 2);
        roleService.createRole("PAGE_INFO", true, 2);
        roleService.createRole("PAGE_PHONE", false, 2);
        roleService.createRole("PAGE_FEEDING", false, 2);
        roleService.createRole("PAGE_DOINGS", true, 2);
    }

    @Override
    public void run() {
        if (roleService.getRecordsCount() == 0) {
            initRoles();
        }
    }
}
