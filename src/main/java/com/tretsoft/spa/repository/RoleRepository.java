package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.domain.SpaRole;
import com.tretsoft.spa.model.domain.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<SpaRole, Long> {

}
