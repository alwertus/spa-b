package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsRepository extends JpaRepository<Sms, Long> {

    List<Sms> findByModemId(Long modemId);

}
