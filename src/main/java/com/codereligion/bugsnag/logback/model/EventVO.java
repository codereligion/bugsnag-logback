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

/**
 * Represents a bugsnag error event.
 *
 * @author Sebastian Gröbler
 */
public class EventVO {

    /**
     * A unique identifier for a user affected by this event.
     */
    private String userId;

    /**
     * The version number of the application which generated the error.
     */
    private String appVersion;

    /**
     * The operating system version of the client that the error was
     * generated on.
     */
    private String osVersion;

    /**
     *  The release stage that this error occurred in.
     */
    private String releaseStage;

    /**
     * A string representing what was happening in the application at the
     * time of the error. This string could be used for grouping purposes,
     * depending on the event.
     */
    private String context;

    /**
     * All errors with the same groupingHash will be grouped together within
     * the bugsnag dashboard.
     */
    private String groupingHash;

    /**
     * An object containing any further data which should be attached to this event.
     */
    private MetaDataVO metaData;

    /**
     * A list representation of the nested exceptions contained by the causing logging event.
     */
    private List<ExceptionVO> exceptions = Lists.newArrayList();

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(final String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(final String osVersion) {
        this.osVersion = osVersion;
    }

    public String getReleaseStage() {
        return releaseStage;
    }

    public void setReleaseStage(final String releaseStage) {
        this.releaseStage = releaseStage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public String getGroupingHash() {
        return groupingHash;
    }

    public void setGroupingHash(final String groupingHash) {
        this.groupingHash = groupingHash;
    }

    public MetaDataVO getMetaData() {
        return metaData;
    }

    public void setMetaData(final MetaDataVO metaData) {
        this.metaData = metaData;
    }

    public List<ExceptionVO> getExceptions() {
        return exceptions;
    }

    public void addExceptions(final List<ExceptionVO> exceptions) {
        this.exceptions.addAll(exceptions);
    }
}
