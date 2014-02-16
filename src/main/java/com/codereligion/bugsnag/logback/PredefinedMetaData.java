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
package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * An enumeration of all structurally embedded meta data in the bugsnag model.
 *
 * @author Sebastian Gröbler
 */
public enum PredefinedMetaData {

    USER_ID("userId"),
    APP_VERSION("appVersion"),
    OS_VERSION("osVersion"),
    CONTEXT("context"),
    GROUPING_HASH("groupingHash");

    private final String key;

    private PredefinedMetaData(final String key) {
        this.key = key;
    }

    /**
     * @return the key this enumeration maps to
     */
    public String getKey() {
        return key;
    }

    /**
     * Tries to find the value for the given {@code key} in the following contexts in the given order:
     *
     * <ul>
     *     <li>mapped diagnostic context (MDC)</li>
     *     <li>logger context</li>
     *     <li>system properties</li>
     * </ul>
     *
     * @param loggingEvent the {@link ILoggingEvent} to get the MDC or logger context map from
     * @return the value for the given {@code key} or {@code null} if none was found
     */
    public String valueFor(final ILoggingEvent loggingEvent) {

        final String mdcProperty = loggingEvent.getMDCPropertyMap().get(key);

        if (mdcProperty != null) {
            return mdcProperty;
        }

        final String contextProperty = loggingEvent.getLoggerContextVO().getPropertyMap().get(key);

        if (contextProperty != null) {
            return contextProperty;
        }

        final String systemProperty = System.getProperty(key);

        if (systemProperty != null) {
            return systemProperty;
        }

        return null;
    }
}
