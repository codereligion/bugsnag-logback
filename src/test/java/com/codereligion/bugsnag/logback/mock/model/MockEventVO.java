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
package com.codereligion.bugsnag.logback.mock.model;

import com.codereligion.bugsnag.logback.model.EventVO;
import com.codereligion.bugsnag.logback.model.ExceptionVO;
import com.codereligion.bugsnag.logback.model.MetaDataVO;

public class MockEventVO extends EventVO {

    public static MockEventVO createEventVO() {
        return new MockEventVO();
    }

    public MockEventVO withReleaseStage(final String releaseStage) {
        setReleaseStage(releaseStage);
        return this;
    }

    public MockEventVO add(final ExceptionVO exceptionVO) {
        getExceptions().add(exceptionVO);
        return this;
    }

    public MockEventVO withUserId(final String userId) {
        setUserId(userId);
        return this;
    }

    public MockEventVO withAppVersion(final String appVersion) {
        setAppVersion(appVersion);
        return this;
    }

    public MockEventVO withOsVersion(final String osVersion) {
        setOsVersion(osVersion);
        return this;
    }

    public MockEventVO withContext(final String context) {
        setContext(context);
        return this;
    }

    public MockEventVO withGroupingHash(final String groupingHash) {
        setGroupingHash(groupingHash);
        return this;
    }

    public MockEventVO withSeverity(final String severity) {
        setSeverity(severity);
        return this;
    }

    public MockEventVO with(final MetaDataVO metaData) {
        setMetaData(metaData);
        return this;
    }
}
