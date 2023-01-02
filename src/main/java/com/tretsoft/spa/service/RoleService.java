package com.tretsoft.spa.service;

import com.tretsoft.spa.model.SpaRole;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    SpaUser addDefaultRoles(SpaUser user) {
        List<SpaRole> newRoles = roleRepository.findByIsDefaultIsTrue();
        if (user.getRoles() != null)
            newRoles.addAll(user.getRoles());
        user.setRoles(newRoles);
        return user;
    }

    SpaRole createRole(String name, boolean isDefault) {
        log.info("Create " + (isDefault ? "default " : "") + "role: " + name);
        SpaRole newRole = SpaRole.builder()
                .created(Calendar.getInstance())
                .name(name)
                .isDefault(isDefault)
                .build();

        return roleRepository.save(newRole);
    }

    long getRecordsCount() {
        return roleRepository.count();
    }
}
