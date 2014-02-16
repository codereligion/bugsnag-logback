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

import ch.qos.logback.core.spi.ContextAware;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.mock.model.MockNotificationVO.createNotificationVO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class SenderTest {

    private final Configuration configuration = spy(new Configuration());
    private final ContextAware contextAware = mock(ContextAware.class);
    private final Sender sender = new Sender();

    @Test
    public void isStartedAfterStart() {
        sender.start(mock(Configuration.class), mock(ContextAware.class));
        assertThat(sender.isStarted(), is(true));
    }

    @Test
    public void isStoppedAfterStop() {
        sender.start(mock(Configuration.class), mock(ContextAware.class));
        sender.stop();
        assertThat(sender.isStopped(), is(true));
    }

    @Test
    public void doesNotSendWhenStopped() {
        sender.start(configuration, contextAware);
        sender.stop();

        reset(configuration, contextAware);

        sender.send(createNotificationVO());
        verifyZeroInteractions(configuration, contextAware);
    }

    @Test
    public void responseClosingIsNullSafe() {
        configuration.setEndpoint("notexisting");

        sender.start(configuration, contextAware);
        sender.send(createNotificationVO());
    }

    @Test
    public void addsErrorWhenRequestCausedException() {

        // given
        configuration.setEndpoint("notexisting");

        // when
        sender.start(configuration, contextAware);
        sender.send(createNotificationVO());

        // then
        verify(contextAware).addError(eq("Could not deliver notification, unexpected exception occurred."), any(Throwable.class));
    }
}
