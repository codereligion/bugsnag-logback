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

/**
 * Static information about this notifier.
 *
 * @author Sebastian Gröbler
 */
public class NotifierVO {

    private static final String NOTIFIER_NAME = "Bugsnag Logback Notifier";
    private static final String NOTIFIER_VERSION = "1.0.0";
    private static final String NOTIFIER_URL = "https://github.com/codereligion/bugsnag-logback";

    /**
     * The notifier name
     */
    private final String name = NOTIFIER_NAME;

    /**
     * The notifier's current version.
     */
    private final String version = NOTIFIER_VERSION;

    /**
     * The URL associated with the notifier
     */
    private final String url = NOTIFIER_URL;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }
}
