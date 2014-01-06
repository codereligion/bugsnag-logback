package com.bugsnag;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.bugsnag.logging.MockLoggingEvent;
import com.bugsnag.resource.NotifierResource;
import javax.ws.rs.client.Client;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static com.bugsnag.logging.MockLoggingEvent.createLoggingEvent;
import static com.bugsnag.logging.MockThrowableProxy.createThrowableProxy;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore("wip")
public class AppenderTest {

    // TODO test that start sets start flag
    // TODO test that configuration is passed to client
    // TODO test conversion of string to array of strings
    // TODO test corner cases of configuration (null, empty strings)
    // TODO test default values of configuration
    // TODO test that append does not appender if not started
    // TODO test that only exceptions are send

    @Mock
    private Client client;

    @InjectMocks
    private Appender appender;

    private NotifierResource notifierResource;

    @Before
    public void beforeEachTest() {

        final ResteasyWebTarget resteasyWebTarget = mock(ResteasyWebTarget.class);
        notifierResource = mock(NotifierResource.class);

        when(resteasyWebTarget.proxy(NotifierResource.class)).thenReturn(notifierResource);
        when(client.target(anyString())).thenReturn(resteasyWebTarget);

        appender.setApiKey("some api key");
    }

    @Test
    public void doesNotAppendWhenReleaseStageIsIgnored() {
        // given
        final ILoggingEvent loggingEvent = createLoggingEvent()
                .withThrowableProxy(createThrowableProxy());

        appender.setNotifyReleaseStages("staging,production");
        appender.setReleaseStage("test");

        // when
        appender.start();
        appender.append(loggingEvent);

        // then
        verifyZeroInteractions(notifierResource);
    }

    @Test
    public void doesNotAppendWhenExceptionClassIsIgnored() {
        // given
        final ILoggingEvent loggingEvent = createLoggingEvent()
                .withThrowableProxy(
                        createThrowableProxy().withClassName("some.foo.Bar"));

        appender.setIgnoreClasses("some.foo.Bar");

        // when
        appender.start();
        appender.append(loggingEvent);

        // then
        verifyZeroInteractions(notifierResource);
    }


    @Test
    public void logsInfoStatusOnSuccessfulRequest() {
        // given
        // mock client

        // when
        appender.start();
        final MockLoggingEvent loggingEvent = createLoggingEvent().withThrowableProxy(createThrowableProxy());
        appender.doAppend(loggingEvent);

        // then
    }

    @Test
    public void logsErrorStatusOnBadRequest() {
        // given

        // when
        appender.start();
        final MockLoggingEvent loggingEvent = createLoggingEvent().withThrowableProxy(createThrowableProxy());
        appender.doAppend(loggingEvent);

        // then
        verify(1, postRequestedFor(urlEqualTo("/")));
    }

    @Test
    public void logsErrorStatusOnUnauthorizedRequest() {
        // TODO
    }

    @Test
    public void logsErrorStatusOnTooLargeRequest() {
        // TODO
    }


    @Test
    public void logsErrorStatusOnTooManyRequests() {
        // TODO
    }
}
