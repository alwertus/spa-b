package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.SpaRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<SpaRole, Long> {
    List<SpaRole> findByIsDefaultIsTrue();
}