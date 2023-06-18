package com.tretsoft.spa.web.controller.admin;

import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.service.auth.RoleService;
import com.tretsoft.spa.service.auth.UserService;
import com.tretsoft.spa.web.dto.admin.AdminUserDto;
import com.tretsoft.spa.web.mapper.admin.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-user")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;
    private final AdminUserMapper mapper;
    @GetMapping
    public List<AdminUserDto> getUserList() {
        List<SpaUser> users = userService.getAllUsers();
        return mapper.sourcesToDtos(users);
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return roleService
                .getAll()
                .stream()
                .map(SpaRole::getName)
                .collect(Collectors.toList());
    }

}
