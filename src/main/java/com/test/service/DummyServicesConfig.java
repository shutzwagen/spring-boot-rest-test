package com.test.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.test.Main.LOGGER;

/**
 * //TODO add comment before commit
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
@Configuration
public class DummyServicesConfig {

    private static final String DUMMY_SERVICE_INITIALIZED_MESSAGE = "Dummy service {} has been instantiated";

    @Service("FirstDummyService")
    class FirstDummyService implements IDummyService {

        @PostConstruct
        @Override
        public void printBeanName() {
            LOGGER.info(DUMMY_SERVICE_INITIALIZED_MESSAGE, "FirstDummyService");
        }
    }


    @Service("SecondDummyService")
    class SecondDummyService implements IDummyService {

        @PostConstruct
        @Override
        public void printBeanName() {
            LOGGER.info(DUMMY_SERVICE_INITIALIZED_MESSAGE, "SecondDummyService");
        }
    }
}
