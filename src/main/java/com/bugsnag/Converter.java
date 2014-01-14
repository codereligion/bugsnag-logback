package com.bugsnag;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.bugsnag.model.EventVO;
import com.bugsnag.model.ExceptionVO;
import com.bugsnag.model.MetaDataVO;
import com.bugsnag.model.NotificationVO;
import com.bugsnag.model.StackTraceVO;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

public class Converter {

    public static final String USER_ID = "userId";
    public static final String APP_VERSION = "appVersion";
    public static final String OS_VERSION = "osVersion";
    public static final String CONTEXT = "context";
    public static final String GROUPING_HASH = "groupingHash";

    private final Configuration configuration;
    private final Optional<MetaDataProvider> metaDataProvider;

   public Converter(final Configuration configuration) {
        this.configuration = configuration;
        this.metaDataProvider = initializeMetaDataProvider();
    }

    private Optional<MetaDataProvider> initializeMetaDataProvider() {
        if (configuration.hasMetaDataProvider()) {

            try {
                final Optional<String> metaDataProviderClassName = configuration.getMetaDataProviderClassName();
                final Class<?> metaDataProviderClass = Class.forName(metaDataProviderClassName.get());
                return Optional.of((MetaDataProvider) metaDataProviderClass.newInstance());
            } catch (final InstantiationException e) {
                throw new IllegalStateException(e);
            } catch (final IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (final ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return Optional.absent();
    }

    public NotificationVO convertToNotification(final ILoggingEvent event) {
        final NotificationVO notification = new NotificationVO();
        notification.setApiKey(configuration.getApiKey());
        notification.addEvents(convertToEvents(event));
        return notification;
    }

    private List<EventVO> convertToEvents(final ILoggingEvent loggingEvent) {
        final EventVO event = new EventVO();
        event.setReleaseStage(configuration.getReleaseStage());
        event.addExceptions(convertToExceptions(loggingEvent.getThrowableProxy()));
        event.setMetaData(convertToMetaData(loggingEvent));
        event.setUserId(getValueFor(loggingEvent, USER_ID));
        event.setAppVersion(getValueFor(loggingEvent, APP_VERSION));
        event.setOsVersion(getValueFor(loggingEvent, OS_VERSION));
        event.setContext(getValueFor(loggingEvent, CONTEXT));
        event.setGroupingHash(getValueFor(loggingEvent, GROUPING_HASH));
        return Collections.singletonList(event);
    }

    private MetaDataVO convertToMetaData(final ILoggingEvent loggingEvent) {
        if (metaDataProvider.isPresent()) {
            return metaDataProvider.get().provide(loggingEvent);
        }
        return null;
    }

    private String getValueFor(final ILoggingEvent loggingEvent, final String key) {

        final String mdcProperty = loggingEvent.getMDCPropertyMap().get(key);

        if (mdcProperty != null) {
            return mdcProperty;
        }

        final String contextProperty = loggingEvent.getLoggerContextVO().getPropertyMap().get(key);

        if (contextProperty != null) {
            return contextProperty;
        }

        final String systemProperty = System.getProperty(key);

        if (systemProperty != null) {
            return systemProperty;
        }

        return null;
    }

    private List<ExceptionVO> convertToExceptions(final IThrowableProxy throwableProxy) {
        final List<ExceptionVO> exceptions = Lists.newArrayList();
        addExceptionsRecursively(throwableProxy, exceptions);
        return exceptions;
    }

    private void addExceptionsRecursively(final IThrowableProxy throwableProxy, final List<ExceptionVO> exceptions) {
        final ExceptionVO exception = new ExceptionVO();
        exception.setErrorClass(throwableProxy.getClassName());
        exception.setMessage(throwableProxy.getMessage());
        exception.addStacktrace(convertToStackTraces(throwableProxy));
        exceptions.add(exception);

        final boolean hasCause = throwableProxy.getCause() != null;
        if (hasCause) {
            addExceptionsRecursively(throwableProxy.getCause(), exceptions);
        }
    }

    private List<StackTraceVO> convertToStackTraces(final IThrowableProxy throwableProxy) {
        final StackTraceElementProxy[] stackTraceElementProxies = throwableProxy.getStackTraceElementProxyArray();
        final List<StackTraceVO> stackTraces = Lists.newArrayList();
        for (final StackTraceElementProxy stackTraceElementProxy : stackTraceElementProxies) {
            final StackTraceElement stackTraceElement = stackTraceElementProxy.getStackTraceElement();

            final StackTraceVO stackTrace = new StackTraceVO();
            stackTrace.setMethod(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName());
            stackTrace.setLineNumber(stackTraceElement.getLineNumber());
            stackTrace.setFile(stackTraceElement.getFileName());
            stackTrace.setInProject(configuration.isInProject(stackTraceElement.getClassName()));

            stackTraces.add(stackTrace);
        }

        return stackTraces;
    }
}