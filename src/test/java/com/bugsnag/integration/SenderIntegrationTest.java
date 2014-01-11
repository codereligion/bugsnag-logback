package com.bugsnag.integration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import com.bugsnag.Configuration;
import com.bugsnag.Sender;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Rule;
import org.junit.Test;
import static com.bugsnag.logging.MockLoggingEvent.createLoggingEvent;
import static com.bugsnag.logging.MockThrowableProxy.createThrowableProxy;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.mockito.Mockito.mock;

public class SenderIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    private Sender sender = new Sender();

    /**
     * Tests roughly that connections are closed correctly.
     */
    @Test
    public void canSendMultipleRequests() {

        // given
        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Response.Status.BAD_REQUEST.getStatusCode())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        final Configuration configuration = new Configuration();
        configuration.setEndpoint("localhost:8089");

        final ContextAware contextAware = mock(ContextAware.class);
        sender.start(configuration, contextAware);

        // when
        final ILoggingEvent loggingEvent = createLoggingEvent().with(createThrowableProxy());
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);

        // then
        verify(5, postRequestedFor(urlEqualTo("/")));
    }
}
