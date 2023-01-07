package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.Modem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Modem, Long> {
}
