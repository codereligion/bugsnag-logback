package com.bugsnag.model;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StackTraceVOTest {

    @Test
    public void defaultsToNotInProject() {
        assertThat(new StackTraceVO().isInProject(), is(false));
    }
}
