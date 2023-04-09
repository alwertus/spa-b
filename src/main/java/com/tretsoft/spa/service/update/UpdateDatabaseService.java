package com.tretsoft.spa.service.update;

import com.tretsoft.spa.model.config.AppConfig;
import com.tretsoft.spa.repository.AppConfigRepository;
import com.tretsoft.spa.service.update.handler.UpdateHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class UpdateDatabaseService {

    public static final String ATTR_VERSION = "version";

    private final AppConfigRepository appConfigRepository;

    private final ApplicationContext applicationContext;

    @PostConstruct
    public void beginUpdate() {
        AppConfig attrVersion = appConfigRepository
                .findByAttribute(ATTR_VERSION)
                .orElse(AppConfig.builder().attribute(ATTR_VERSION).value(null).build());

        log.info("DB Schema version: " + attrVersion.getValue());

        List<UpdateHandler> handlers = getVersionHandlers();

        try {
            while (true) {
                UpdateHandler handler = handlers.stream()
                        .filter(el -> el.oldVersion == null && attrVersion.getValue() == null || el.oldVersion != null && el.oldVersion.equals(attrVersion.getValue()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Finish"));

                update(attrVersion, handler);
            }
        } catch (NotFoundException ex) {
            log.info("DB schema version has actual version: " + attrVersion.getValue());
        } catch (Exception ex) {
            log.error("Error while update database from version: " + attrVersion.getValue(), ex);
            SpringApplication.exit(applicationContext);
        }

    }

    private List<UpdateHandler> getVersionHandlers() {
        List<UpdateHandler> childBeans = new ArrayList<>();
        Map<String, UpdateHandler> beansOfType = applicationContext.getBeansOfType(UpdateHandler.class);

        for (UpdateHandler bean : beansOfType.values()) {
            if (!bean.getClass().isAssignableFrom(UpdateHandler.class)) {
                childBeans.add(bean);
            }
        }
        return childBeans;
    }

    @Transactional
    void update(AppConfig attrVersion, UpdateHandler handler) {
        handler.run();
        attrVersion.setValue(handler.newVersion);
        appConfigRepository.save(attrVersion);
        log.info("DB schema updated to version: " + attrVersion.getValue());
    }

}
