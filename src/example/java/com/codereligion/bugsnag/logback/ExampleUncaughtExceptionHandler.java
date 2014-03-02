package com.codereligion.bugsnag.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOG.error("Uncaught exception", t);
    }
}
