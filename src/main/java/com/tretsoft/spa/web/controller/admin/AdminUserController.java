package com.tretsoft.spa.web.controller.admin;

import com.tretsoft.spa.model.user.SpaRole;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.service.auth.AuthenticationService;
import com.tretsoft.spa.service.auth.RoleService;
import com.tretsoft.spa.service.auth.UserService;
import com.tretsoft.spa.web.controller.ExceptionHandlerController;
import com.tretsoft.spa.web.dto.admin.AdminUserDto;
import com.tretsoft.spa.web.dto.admin.UserRoleDto;
import com.tretsoft.spa.web.mapper.admin.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin-user")
public class AdminUserController extends ExceptionHandlerController {

    private final UserService userService;
    private final RoleService roleService;
    private final AdminUserMapper mapper;
    private final AuthenticationService authenticationService;

    protected void logInfo(String message) {
        log.info("[" + authenticationService.getCurrentUser().getLogin() + "]: " + this.getClass().getSimpleName() + ". " + message);
    }

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

    @PostMapping("/role")
    public AdminUserDto addRoleToUser(@Validated @RequestBody UserRoleDto userRoleDto) {
        logInfo("Add role to user: " + userRoleDto);
        return mapper.sourceToDto(userService.addRoleToUser(
                userService.getUserByLogin(userRoleDto.getUserLogin()),
                roleService.getByName(userRoleDto.getRoleName())));
    }

    @DeleteMapping("/role")
    public AdminUserDto removeRoleFromUser(@Validated @RequestBody UserRoleDto userRoleDto) {
        logInfo("Remove role from user: " + userRoleDto);

        return mapper.sourceToDto(userService.removeRoleFromUser(
                userService.getUserByLogin(userRoleDto.getUserLogin()),
                roleService.getByName(userRoleDto.getRoleName())));
    }

}
