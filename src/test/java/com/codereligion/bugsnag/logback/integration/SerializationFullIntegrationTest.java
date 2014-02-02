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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import com.codereligion.bugsnag.logback.Appender;
import com.codereligion.bugsnag.logback.Configuration;
import com.codereligion.bugsnag.logback.mock.logging.MockStackTraceElement;
import com.codereligion.bugsnag.logback.mock.logging.MockThrowableProxy;
import com.codereligion.bugsnag.logback.mock.model.MockExceptionVO;
import com.codereligion.bugsnag.logback.mock.model.MockMetaDataVO;
import com.codereligion.bugsnag.logback.mock.model.MockNotificationVO;
import com.codereligion.bugsnag.logback.resource.GsonProvider;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.mock.logging.MockLoggingEvent.createLoggingEvent;
import static com.codereligion.bugsnag.logback.mock.model.MockEventVO.createEventVO;
import static com.codereligion.bugsnag.logback.mock.model.MockStackTraceVO.createStackTraceVO;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.mockito.Mockito.mock;

public class SerializationFullIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(IntegrationTestConfiguration.PORT);

    private Appender appender = new Appender();

    private final Gson gson = new GsonProvider(new Configuration()).getGson();

    @Before
    public void beforeEachTest() {
        appender.setApiKey("someApiKey");
        appender.setEndpoint(IntegrationTestConfiguration.ENDPOINT);
        appender.setReleaseStage("test");
        appender.setNotifyReleaseStages("test");
        appender.setContext(mock(Context.class));
    }

    @Test
    public void serializesNotificationCorrectly() {

        // given
        appender.setProjectPackages("some.project.package");
        appender.setMetaDataProvider(CustomMetaDataProvider.class.getCanonicalName());
        appender.setFilters("password");

        stubFor(post(urlEqualTo("/"))
                .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON))
                .willReturn(aResponse()
                        .withStatus(Response.Status.OK.getStatusCode())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)));

        final ILoggingEvent loggingEvent = createLoggingEvent()
                .withLevel(Level.ERROR)
                .withMessage("someMessage")
                .withMdcProperty("userId", "someUserId")
                .withMdcProperty("password", "v3rys3cr3t")
                .withSystemProperty("appVersion", "someAppVersion")
                .withSystemProperty("osVersion", "someOsVersion")
                .withContextProperty("context", "someContext")
                .withContextProperty("groupingHash", "someGroupingHash")
                .with(MockThrowableProxy.createThrowableProxy()
                                .withClassName("some.project.package.Class")
                                .withMessage("some message")
                                .addStackTraceElementProxy(
                                        MockStackTraceElement.createStackTraceElement()
                                                .withDeclaringClass("some.project.package.Class")
                                                .withFileName("SomeFile.java")
                                                .withLineNumber(42)
                                                .withMethodName("someMethodName")
                                )
                                .withCause(MockThrowableProxy.createThrowableProxy()
                                        .withClassName("some.project.package.OtherClass")
                                        .withMessage("some other message")
                                        .addStackTraceElementProxy(
                                                MockStackTraceElement.createStackTraceElement()
                                                        .withDeclaringClass("some.project.package.OtherSomeFile")
                                                        .withFileName("OtherSomeFile.java")
                                                        .withLineNumber(21)
                                                        .withMethodName("someOtherMethodName")
                                        )
                                )
                )
                ;

        final MockNotificationVO expectedNotification = MockNotificationVO.createNotificationVO()
                .withApiKey("someApiKey")
                .add(createEventVO()
                        .withReleaseStage("test")
                        .withUserId("someUserId")
                        .withAppVersion("someAppVersion")
                        .withOsVersion("someOsVersion")
                        .withContext("someContext")
                        .withGroupingHash("someGroupingHash")
                        .with(MockMetaDataVO.createMetaDataVO()
                                .add("Logging", "level", "ERROR")
                                .add("Logging", "message", "someMessage")
                                .add("User", null, null)
                        )
                        .add(MockExceptionVO.createExceptionVO()
                                .withErrorClass("some.project.package.Class")
                                .withMessage("some message")
                                .add(createStackTraceVO()
                                        .withFile("SomeFile.java")
                                        .withLineNumber(42)
                                        .withMethod("some.project.package.Class.someMethodName")
                                        .whichIsInProject()
                                )
                        )
                        .add(MockExceptionVO.createExceptionVO()
                                .withErrorClass("some.project.package.OtherClass")
                                .withMessage("some other message")
                                .add(createStackTraceVO()
                                        .withFile("OtherSomeFile.java")
                                        .withLineNumber(21)
                                        .withMethod("some.project.package.OtherSomeFile.someOtherMethodName")
                                        .whichIsInProject()
                                )
                        )
                );

        // when
        appender.start();
        appender.doAppend(loggingEvent);

        // then
        verify(1, postRequestedFor(urlEqualTo("/")).withRequestBody(equalTo(gson.toJson(expectedNotification))));
    }

}
