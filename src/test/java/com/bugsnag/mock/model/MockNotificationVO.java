package com.bugsnag.mock.model;

import com.bugsnag.model.EventVO;
import com.bugsnag.model.NotificationVO;

public class MockNotificationVO extends NotificationVO {

    public static MockNotificationVO createNotificationVO() {
        return new MockNotificationVO();
    }

    public MockNotificationVO add(final EventVO eventVO) {
        getEvents().add(eventVO);
        return this;
    }

    public MockNotificationVO withApiKey(final String apiKey) {
        setApiKey(apiKey);
        return this;
    }
}
