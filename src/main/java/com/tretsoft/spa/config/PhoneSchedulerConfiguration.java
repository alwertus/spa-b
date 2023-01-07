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

    @Scheduled(fixedDelay = 1000*60*60)
    public void test() {
        for (PhoneGsmService phone : phoneList) {
            phone.initialize();
            ArrayList<PhoneSms> sms = phone.readSms();
            for (PhoneSms s : sms) {
                log.info(s);
            }
            phone.closePort();
        }
    }
}
