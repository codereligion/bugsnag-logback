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

import ch.qos.logback.core.spi.ContextAware;
import com.codereligion.bugsnag.logback.Configuration;
import com.codereligion.bugsnag.logback.Sender;
import com.codereligion.bugsnag.logback.model.NotificationVO;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import static com.codereligion.bugsnag.logback.mock.model.MockNotificationVO.createNotificationVO;
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
    public final WireMockRule wireMockRule = new WireMockRule(IntegrationTestConfiguration.PORT);

    private final Configuration configuration = new Configuration();
    private final ContextAware contextAware = mock(ContextAware.class);
    private final Sender sender = new Sender();

    @Before
    public void beforeEachTest() {
        configuration.setEndpoint(IntegrationTestConfiguration.ENDPOINT);
        sender.start(configuration, contextAware);
    }

    /**
     * Tests roughly that connections are closed correctly.
     */
    @Test
    public void canSendMultipleRequests() {

        // given
        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Response.Status.OK.getStatusCode())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        // when
        final NotificationVO notification = createNotificationVO();
        sender.send(notification);
        sender.send(notification);
        sender.send(notification);
        sender.send(notification);
        sender.send(notification);

        // then
        verify(5, postRequestedFor(urlEqualTo("/")));
    }

    @Test
    public void noOpsOnSendWhenStopped() {
        // given
        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Response.Status.OK.getStatusCode())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        // when
        sender.stop();
        sender.send(createNotificationVO());
        verify(0, postRequestedFor(urlEqualTo("/")));
    }

    @Test
    public void addsErrorOnBadRequest() {

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Sender.StatusCode.BAD_REQUEST)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        sender.send(createNotificationVO());

        Mockito.verify(contextAware).addError("Could not deliver notification to bugsnag, got http status code: 400");
    }

    @Test
    public void addsErrorOnUnauthorizedRequest() {

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Sender.StatusCode.UNAUTHORIZED)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        sender.send(createNotificationVO());

        Mockito.verify(contextAware).addError("Could not deliver notification to bugsnag, got http status code: 401");
    }

    @Test
    public void addsErrorOnTooLargeRequestEntity() {

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Sender.StatusCode.REQUEST_ENTITY_TOO_LARGE)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        sender.send(createNotificationVO());

        Mockito.verify(contextAware).addError("Could not deliver notification to bugsnag, got http status code: 413");
    }

    @Test
    public void addsErrorOnTooManyRequests() {

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Sender.StatusCode.TOO_MANY_REQUESTS)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        sender.send(createNotificationVO());

        Mockito.verify(contextAware).addError("Could not deliver notification to bugsnag, got http status code: 429");
    }

    @Test
    public void addsErrorOnUnknownStatusCode() {

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(420)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        sender.send(createNotificationVO());

        Mockito.verify(contextAware).addError("Unexpected http status code: 420");
    }
}
