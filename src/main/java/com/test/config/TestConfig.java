package com.test.config;

import com.test.service.IDummyComponentBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

import static com.test.Main.LOGGER;

/**
 * Profile-based configuration for TEST environment.
 *
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
@Profile("test")
@Configuration
public class TestConfig {

    private static final String DUMMY_COMPONENT_INITIALIZED_MESSAGE =
            "Dummy component for test environment has been instantiated";

    @Bean
    IDummyComponentBean getDummyTestBean() {
        return new IDummyComponentBean() {

            @PostConstruct
            @Override
            public void printActiveProfile() {
                LOGGER.info(DUMMY_COMPONENT_INITIALIZED_MESSAGE);
            }
        };
    }

}