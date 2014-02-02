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
package com.codereligion.bugsnag.logback;

import com.codereligion.bugsnag.logback.model.NotificationVO;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static com.codereligion.bugsnag.logback.mock.logging.MockLoggingEvent.createLoggingEvent;
import static com.codereligion.bugsnag.logback.mock.logging.MockThrowableProxy.createThrowableProxy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppenderTest {

    @Mock
    private Configuration configuration;

    @Mock
    private Sender sender;

    @InjectMocks
    private Appender appender;

    @Test
    public void doesNotStartWhenConfigurationIsInvalid() {

        when(configuration.isInvalid()).thenReturn(Boolean.TRUE);

        appender.start();

        assertThat(appender.isStarted(), is(Boolean.FALSE));
    }

    @Test
    public void addsErrorsWhenConfigurationIsInvalid() {

        when(configuration.isInvalid()).thenReturn(Boolean.TRUE);

        appender.start();

        verify(configuration).addErrors(appender);
    }

    @Test
    public void startsWhenConfigurationIsValid() {

        when(configuration.isInvalid()).thenReturn(Boolean.FALSE);

        appender.start();

        assertThat(appender.isStarted(), is(Boolean.TRUE));
    }

    @Test
    public void startsSenderOnAppenderStart() {

        when(configuration.isInvalid()).thenReturn(Boolean.FALSE);

        appender.start();

        verify(sender).start(configuration, appender);
    }

    @Test
    public void doesNotStopWhenNotStarted() {
        appender.stop();
        verifyZeroInteractions(sender);
    }

    @Test
    public void stopsSenderOnAppenderStop() {

        // given
        when(configuration.isInvalid()).thenReturn(Boolean.FALSE);

        // when
        appender.start();
        appender.stop();

        // then
        verify(sender).stop();
        assertThat(appender.isStarted(), is(Boolean.FALSE));
    }

    @Test
    public void doesNotAppendWhenNotStarted() {

        appender.append(createLoggingEvent().with(createThrowableProxy()));

        verifyZeroInteractions(sender);
    }

    @Test
    public void doesNotAppendWhenStageIsIgnored() {

        // given
        when(configuration.isStageIgnored()).thenReturn(Boolean.TRUE);

        // when
        appender.start();
        appender.append(createLoggingEvent().with(createThrowableProxy()));

        // then
        verify(sender, never()).send(any(NotificationVO.class));
    }

    @Test
    public void doesNotAppendWhenEventDoesNotContainAnException() {
        appender.start();
        appender.append(createLoggingEvent());

        verify(sender, never()).send(any(NotificationVO.class));
    }

    @Test
    public void doesNotAppendWhenExceptionIsIgnored() {

        // given
        when(configuration.shouldNotifyFor(anyString())).thenReturn(Boolean.FALSE);

        // when
        appender.start();
        appender.append(createLoggingEvent().with(createThrowableProxy()));

        // then
        verify(sender, never()).send(any(NotificationVO.class));
    }

    @Test
    public void delegatesSslEnabledToConfiguration() {
        appender.setSslEnabled(true);
        verify(configuration).setSslEnabled(true);
    }

    @Test
    public void delegatesEndpointToConfiguration() {
        appender.setEndpoint("some endpoint");
        verify(configuration).setEndpoint("some endpoint");
    }

    @Test
    public void delegatesApiKeyToConfiguration() {
        appender.setApiKey("some api key");
        verify(configuration).setApiKey("some api key");
    }

    @Test
    public void delegatesReleaseStageToConfiguration() {
        appender.setReleaseStage("test");
        verify(configuration).setReleaseStage("test");
    }

    @Test
    public void delegatesNotifyReleaseStagesToConfiguration() {
        appender.setNotifyReleaseStages("test,live");
        verify(configuration).setNotifyReleaseStages(Sets.newHashSet("test", "live"));
    }

    @Test
    public void delegatesFiltersToConfiguration() {
        appender.setFilters("pw,password");
        verify(configuration).setFilters(Sets.newHashSet("pw", "password"));
    }

    @Test
    public void delegatesProjectPackagesToConfiguration() {
        appender.setProjectPackages("com.foo,com.bar");
        verify(configuration).setProjectPackages(Sets.newHashSet("com.foo", "com.bar"));
    }

    @Test
    public void delegatesIgnoredClassToConfiguration() {
        appender.setIgnoreClasses("com.foo.Bar,com.rofl.Olikoski");
        verify(configuration).setIgnoreClasses(Sets.newHashSet("com.foo.Bar", "com.rofl.Olikoski"));
    }

    @Test
    public void delegatesMetaDataProviderToConfiguration() {
        appender.setMetaDataProvider("com.foo.MetaDataProvider");
        verify(configuration).setMetaDataProviderClassName("com.foo.MetaDataProvider");
    }
}
