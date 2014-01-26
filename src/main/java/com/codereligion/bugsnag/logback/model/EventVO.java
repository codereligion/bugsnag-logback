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

public class EventVO {

    /**
     * A unique identifier for a user affected by this event. This could be
     * any distinct identifier that makes sense for your application/platform.
     * This field is optional but highly recommended.
     */
    private String userId;

    /**
     * The version number of the application which generated the error.
     * (optional, default none)
     */
    private String appVersion;

    /**
     * The operating system version of the client that the error was
     * generated on. (optional, default none)
     */
    private String osVersion;

    /**
     *  The release stage that this error occurred in, for example
     *  "development" or "production". This can be any string, but "production"
     *  will be highlighted differently in bugsnag in the future, so please use
     *  "production" appropriately.
     */
    private String releaseStage;

    /**
     * A string representing what was happening in the application at the
     * time of the error. This string could be used for grouping purposes,
     * depending on the event.
     * Usually this would represent the controller and action in a server
     * based project. It could represent the screen that the user was
     * interacting with in a client side project.
     */
    private String context;

    /**
     * All errors with the same groupingHash will be grouped together within
     * the bugsnag dashboard.
     * This gives a notifier more control as to how grouping should be performed.
     * We recommend including the errorClass of the exception in here so a different
     * class of error will be grouped separately.
     * (optional)
     */
    private String groupingHash;

    /**
     * An object containing any further data you wish to attach to this error
     * event. This should contain one or more objects, with each object being
     * displayed in its own tab on the event details on the Bugsnag website.
     * (Optional).
     */
    private MetaDataVO metaData;

    /**
     * An array of exceptions that occurred during this event. Most of the
     * time there will only be one exception, but some languages support
     * "nested" or "caused by" exceptions. In this case, exceptions should
     * be unwrapped and added to the array one at a time. The first exception
     * raised should be first in this array.
     */
    private List<ExceptionVO> exceptions = Lists.newArrayList();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getReleaseStage() {
        return releaseStage;
    }

    public void setReleaseStage(String releaseStage) {
        this.releaseStage = releaseStage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getGroupingHash() {
        return groupingHash;
    }

    public void setGroupingHash(String groupingHash) {
        this.groupingHash = groupingHash;
    }

    public MetaDataVO getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataVO metaData) {
        this.metaData = metaData;
    }

    public List<ExceptionVO> getExceptions() {
        return exceptions;
    }

    public void addExceptions(List<ExceptionVO> exceptions) {
        this.exceptions.addAll(exceptions);
    }
}
