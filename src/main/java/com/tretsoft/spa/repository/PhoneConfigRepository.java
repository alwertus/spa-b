package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.phone.UserPhoneConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneConfigRepository extends JpaRepository<UserPhoneConfig, Long> {

}
