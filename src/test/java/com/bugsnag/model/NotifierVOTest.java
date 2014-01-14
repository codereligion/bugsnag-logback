package com.bugsnag.model;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotifierVOTest {

    @Test
    public void providesName() {
        assertThat(new NotifierVO().getName(), is("Bugsnag Logback Notifier"));
    }

    @Test
    public void providesVersion() {
        assertThat(new NotifierVO().getVersion(), is("1.0.0"));
    }

    @Test
    public void providesUrl() {
        assertThat(new NotifierVO().getUrl(), is("https://github.com/sierragolf/bugsnag-logback"));
    }
}
