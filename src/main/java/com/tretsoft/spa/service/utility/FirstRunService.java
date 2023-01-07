package com.tretsoft.spa.service.utility;

import com.tretsoft.spa.service.auth.RoleService;
import org.springframework.stereotype.Service;

@Service
public class FirstRunService {

    private final RoleService roleService;


    public FirstRunService(RoleService roleService) {
        this.roleService = roleService;
        if (roleService.getRecordsCount() == 0) {
            initRoles();
        }
    }

    private void initRoles() {
        roleService.createRole("OWNER", false);
        roleService.createRole("ADMIN", false);
        roleService.createRole("USER", true);
        roleService.createRole("PAGE_INFO", true);
        roleService.createRole("PAGE_PHONE", false);
        roleService.createRole("PAGE_FEEDING", false);
    }
}
