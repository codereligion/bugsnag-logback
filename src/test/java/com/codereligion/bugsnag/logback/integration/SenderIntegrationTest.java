/**
 * Copyright 2014 www.codereligion.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codereligion.bugsnag.logback.integration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import com.codereligion.bugsnag.logback.Configuration;
import com.codereligion.bugsnag.logback.Sender;
import com.codereligion.bugsnag.logback.mock.logging.MockThrowableProxy;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Rule;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.mock.logging.MockLoggingEvent.createLoggingEvent;
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
        final ILoggingEvent loggingEvent = createLoggingEvent().with(MockThrowableProxy.createThrowableProxy());
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);
        sender.send(loggingEvent);

        // then
        verify(5, postRequestedFor(urlEqualTo("/")));
    }

    @Test
    public void noOpsOnSendWhenStopped() {
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
        sender.stop();
        sender.send(createLoggingEvent().with(MockThrowableProxy.createThrowableProxy()));
    }
}
