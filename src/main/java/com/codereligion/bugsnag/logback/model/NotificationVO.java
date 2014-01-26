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
package com.codereligion.bugsnag.logback.model;

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

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public List<EventVO> getEvents() {
        return events;
    }

    public void addEvents(final List<EventVO> events) {
        this.events.addAll(events);
    }

    public NotifierVO getNotifier() {
        return notifier;
    }
}
