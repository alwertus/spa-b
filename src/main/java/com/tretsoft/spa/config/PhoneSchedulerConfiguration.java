package com.tretsoft.spa.config;

import com.tretsoft.spa.old.modem.PhoneGsmService;
import com.tretsoft.spa.old.modem.PhoneSms;
import com.tretsoft.spa.service.PhoneService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Configuration
@EnableScheduling
public class PhoneSchedulerConfiguration {
    List<PhoneGsmService> phoneList;

    public PhoneSchedulerConfiguration(PhoneService phoneService) {
        phoneList = phoneService
                .getAllModems()
                .stream()
                .map(e -> new PhoneGsmService(e.getPort()))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelay = 1000*30)
    public void test() {
        log.info("Begin get phone data");
        for (PhoneGsmService phone : phoneList) {
            phone.initialize();
            ArrayList<PhoneSms> smsos = phone.readSms();
            for (PhoneSms s : smsos) {
                log.info(s);
            }
            log.info("Read {} records", smsos.size());
            phone.closePort();
        }
        log.info("End get phone data");
    }
}
