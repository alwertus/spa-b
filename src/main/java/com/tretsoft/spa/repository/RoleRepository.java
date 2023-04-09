package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.SpaRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<SpaRole, Long> {
    List<SpaRole> findByIsDefaultIsTrue();

    Optional<SpaRole> findByName(String name);
}