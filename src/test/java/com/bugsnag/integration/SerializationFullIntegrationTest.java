package com.bugsnag.integration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import com.bugsnag.Appender;
import com.bugsnag.Configuration;
import com.bugsnag.resource.GsonProvider;
import com.bugsnag.mock.model.MockNotificationVO;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static com.bugsnag.mock.logging.MockLoggingEvent.createLoggingEvent;
import static com.bugsnag.mock.logging.MockStackTraceElement.createStackTraceElement;
import static com.bugsnag.mock.logging.MockThrowableProxy.createThrowableProxy;
import static com.bugsnag.mock.model.MockEventVO.createEventVO;
import static com.bugsnag.mock.model.MockExceptionVO.createExceptionVO;
import static com.bugsnag.mock.model.MockMetaDataVO.createMetaDataVO;
import static com.bugsnag.mock.model.MockNotificationVO.createNotificationVO;
import static com.bugsnag.mock.model.MockStackTraceVO.createStackTraceVO;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class SerializationFullIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    private Appender appender = spy(new Appender());

    private final Gson gson = new GsonProvider(new Configuration()).getGson();

    @Before
    public void beforeEachTest() {
        appender.setApiKey("someApiKey");
        appender.setEndpoint("localhost:8089");
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
                .with(createThrowableProxy()
                                .withClassName("some.project.package.Class")
                                .withMessage("some message")
                                .addStackTraceElementProxy(
                                        createStackTraceElement()
                                                .withDeclaringClass("some.project.package.Class")
                                                .withFileName("SomeFile.java")
                                                .withLineNumber(42)
                                                .withMethodName("someMethodName")
                                )
                                .withCause(createThrowableProxy()
                                        .withClassName("some.project.package.OtherClass")
                                        .withMessage("some other message")
                                        .addStackTraceElementProxy(
                                                createStackTraceElement()
                                                        .withDeclaringClass("some.project.package.OtherSomeFile")
                                                        .withFileName("OtherSomeFile.java")
                                                        .withLineNumber(21)
                                                        .withMethodName("someOtherMethodName")
                                        )
                                )
                )
                ;

        final MockNotificationVO expectedNotification = createNotificationVO()
                .withApiKey("someApiKey")
                .add(createEventVO()
                        .withReleaseStage("test")
                        .withUserId("someUserId")
                        .withAppVersion("someAppVersion")
                        .withOsVersion("someOsVersion")
                        .withContext("someContext")
                        .withGroupingHash("someGroupingHash")
                        .with(createMetaDataVO()
                                .add("Logging", "level", "ERROR")
                                .add("Logging", "message", "someMessage")
                                .add("User", null, null)
                        )
                        .add(createExceptionVO()
                                .withErrorClass("some.project.package.Class")
                                .withMessage("some message")
                                .add(createStackTraceVO()
                                        .withFile("SomeFile.java")
                                        .withLineNumber(42)
                                        .withMethod("some.project.package.Class.someMethodName")
                                        .whichIsInProject()
                                )
                        )
                        .add(createExceptionVO()
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
