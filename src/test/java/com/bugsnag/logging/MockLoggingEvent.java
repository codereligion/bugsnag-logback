/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package com.bugsnag.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Marker;

/**
 * A POJO-like implementation of the {@link ch.qos.logback.classic.spi.ILoggingEvent} which allows
 * easy creation of valid logging events, which might be altered through the
 * public setters or the fluent-setters.
 *
 * @author Sebastian Gr&ouml;bler
 */
public class MockLoggingEvent implements ILoggingEvent {


    /**
     * @return a new instance of {@link MockLoggingEvent} with some reasonable default values set
     */
    public static MockLoggingEvent createLoggingEvent() {
        return new MockLoggingEvent();
    }

    private String threadName = "some thread name";
    private Level level = Level.DEBUG;
    private String message = "some message";
    private Object[] argumentArray = new Object[0];
    private String formattedMessage = "some formatted message";
    private String loggerName = "some logger name";
    private LoggerContextVO loggerContextVO = new LoggerContextVO("some name", new HashMap<String, String>(), 0);
    private StackTraceElement[] stackTraceElements;
    private HashMap<String, String> mdcPropertyMap = new HashMap<String, String>();
    private IThrowableProxy throwableProxy;
    private int timeStamp = 0;

    public MockLoggingEvent withThreadName(final String threadName) {
        this.threadName = threadName;
        return this;
    }

    public MockLoggingEvent withLevel(final Level level) {
        this.level = level;
        return this;
    }

    public MockLoggingEvent withMessage(final String message) {
        this.message = message;
        return this;
    }

    public MockLoggingEvent withArgumentArray(final Object[] argumentArray) {
        this.argumentArray = argumentArray;
        return this;
    }

    public MockLoggingEvent withFormattedMessage(final String formattedMessage) {
        this.formattedMessage = formattedMessage;
        return this;
    }

    public MockLoggingEvent withLoggerName(final String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public MockLoggingEvent withLoggerContextVO(final LoggerContextVO loggerContextVO) {
        this.loggerContextVO = loggerContextVO;
        return this;
    }

    public MockLoggingEvent withStackTraceElements(final StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
        return this;
    }

    public MockLoggingEvent withMdcProperty(String key, String value) {
        this.mdcPropertyMap.put(key, value);
        return this;
    }

    public MockLoggingEvent withContextProperty(String key, String value) {
        this.loggerContextVO.getPropertyMap().put(key, value);
        return this;
    }


    public MockLoggingEvent withSystemProperty(String key, String value) {
        System.setProperty(key, value);
        return this;
    }

    public MockLoggingEvent withTimeStamp(final int timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public MockLoggingEvent withThrowableProxy(final MockThrowableProxy throwableProxy) {
        this.throwableProxy = throwableProxy;
        return this;
    }

    @Override
    public String getThreadName() {
        return threadName;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object[] getArgumentArray() {
        return argumentArray;
    }

    @Override
    public String getFormattedMessage() {
        return formattedMessage;
    }

    @Override
    public String getLoggerName() {
        return loggerName;
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return loggerContextVO;
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return throwableProxy;
    }

    @Override
    public StackTraceElement[] getCallerData() {

        if (stackTraceElements == null) {
            stackTraceElements = new StackTraceElement[]{new StackTraceElement("ClassName", "MethodName", "FileName", 1)};
        }

        return stackTraceElements;
    }

    @Override
    public boolean hasCallerData() {

        if (stackTraceElements == null) {
            return false;
        }

        return getCallerData().length > 0;
    }

    @Override
    public Marker getMarker() {
        return null;
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return mdcPropertyMap;
    }

    @Override
    public Map<String, String> getMdc() {
        return getMDCPropertyMap();
    }

    @Override
    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void prepareForDeferredProcessing() {
        // NOOP
    }


}
