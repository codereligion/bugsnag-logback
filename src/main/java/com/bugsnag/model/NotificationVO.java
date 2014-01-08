package com.bugsnag.model;

import com.google.common.collect.Lists;
import java.util.List;

public class NotificationVO {

    /**
     * The API Key associated with the project. Informs Bugsnag which project has generated this error.
     */
    private String apiKey;

    /**
     * This object describes the notifier itself. These properties are used
     * within Bugsnag to track error rates from a notifier.
     */
    private NotifierVO notifier = new NotifierVO();

    /**
     * An array of error events that Bugsnag should be notified of. A notifier
     * can choose to group notices into an array to minimize network traffic, or
     * can notify Bugsnag each time an event occurs.
     */
    private List<EventVO> events = Lists.newArrayList();

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<EventVO> getEvents() {
        return events;
    }

    public void setEvents(List<EventVO> events) {
        this.events = events;
    }
}
