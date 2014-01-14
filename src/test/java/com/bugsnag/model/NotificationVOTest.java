package com.bugsnag.model;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationVOTest {

    @Test
    public void doesNotHaveDefaultApiKey() {
         assertThat(new NotificationVO().getApiKey(), is(nullValue()));
    }

    @Test
    public void containsNotifierInformation() {
        final NotificationVO notificationVO = new NotificationVO();

        assertThat(notificationVO.getNotifier(), is(notNullValue()));
    }
}
