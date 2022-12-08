package com.tretsoft.spa.service;

import com.tretsoft.spa.model.SpaRole;
import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
