package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.domain.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SpaUser, Long> {
    Optional<SpaUser> findByLogin(String login);
    Optional<SpaUser> findByEmailConfirmKey(String emailConfirmKey);

}
