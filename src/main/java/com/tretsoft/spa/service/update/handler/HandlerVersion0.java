package com.tretsoft.spa.service.update.handler;

import com.tretsoft.spa.model.SpaRole;
import com.tretsoft.spa.service.auth.RoleService;
import com.tretsoft.spa.service.auth.UserService;
import org.springframework.stereotype.Component;

@Component
public class HandlerVersion0 extends UpdateHandler {

    private final RoleService roleService;

    private final UserService userService;

    public HandlerVersion0(RoleService roleService, UserService userService) {
        super(null, "1");
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run() {
        SpaRole newRole = roleService.createRole("PAGE_CASH", true);

        userService.getAllUsers()
                .forEach(user -> userService.addRoleToUser(user, newRole));
    }

}
