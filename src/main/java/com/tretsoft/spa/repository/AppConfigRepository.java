package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.config.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    Optional<AppConfig> findByAttribute(String attribute);

}
