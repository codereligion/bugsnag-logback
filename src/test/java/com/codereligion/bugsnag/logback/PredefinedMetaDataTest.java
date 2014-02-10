package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.junit.Before;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.PredefinedMetaData.USER_ID;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PredefinedMetaDataTest {

    private final PredefinedMetaData predefinedMetaData = USER_ID;
    private final ILoggingEvent event = mock(ILoggingEvent.class);

    private final LoggerContextVO context = mock(LoggerContextVO.class);

    @Before
    public void beforeEachTest() {
        // make sure tested property is always removed from system scope before test execution
        System.clearProperty(USER_ID.getKey());

        // logging events must always return a logger context
        when(event.getLoggerContextVO()).thenReturn(context);
    }

    @Test
    public void getsValueFromMdc() {

        when(event.getMDCPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "someValue"));

        final String value = predefinedMetaData.valueFor(event);

        assertThat(value, is("someValue"));
    }

    @Test
    public void getsValueFromContext() {
        // given
        when(context.getPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "someValue"));

        // when
        final String value = predefinedMetaData.valueFor(event);

        // then
        assertThat(value, is("someValue"));
    }

    @Test
    public void getsValueFromSystem() {
        // given
        System.setProperty(USER_ID.getKey(), "someValue");

        // when
        final String value = predefinedMetaData.valueFor(event);

        // then
        assertThat(value, is("someValue"));
    }

    @Test
    public void mdcOverridesContext() {

        // given
        when(event.getMDCPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "mdcValue"));
        when(context.getPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "contextValue"));

        // when
        final String value = predefinedMetaData.valueFor(event);

        // then
        assertThat(value, is("mdcValue"));
    }

    @Test
    public void mdcOverridesSystem() {

        // given
        when(event.getMDCPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "mdcValue"));
        System.setProperty(USER_ID.getKey(), "systemValue");

        // when
        final String value = predefinedMetaData.valueFor(event);

        // then
        assertThat(value, is("mdcValue"));

    }

    @Test
    public void contextOverridesSystem() {

        // given
        when(context.getPropertyMap()).thenReturn(singletonMap(USER_ID.getKey(), "contextValue"));
        System.setProperty(USER_ID.getKey(), "systemValue");

        // when
        final String value = predefinedMetaData.valueFor(event);

        // then
        assertThat(value, is("contextValue"));

    }
}
