package com.bugsnag;

import ch.qos.logback.core.spi.ContextAware;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

// TODO test different response behaviours
public class SenderTest {

    private Sender sender = new Sender();

    @Test
    public void isStartedAfterStart() {
        sender.start(mock(Configuration.class), mock(ContextAware.class));
        assertThat(sender.isStarted(), is(true));
    }

    @Test
    public void isStoppedAfterStart() {
        sender.start(mock(Configuration.class), mock(ContextAware.class));
        sender.stop();
        assertThat(sender.isStopped(), is(true));
    }
}
