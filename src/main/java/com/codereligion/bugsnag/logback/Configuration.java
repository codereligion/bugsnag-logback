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

import ch.qos.logback.core.spi.ContextAware;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import java.util.Set;

public class Configuration {
    private static final String DEFAULT_ENDPOINT = "notify.bugsnag.com";
    private static final boolean DEFAULT_USE_SSL = false;
    private static final String DEFAULT_RELEASE_STAGE = "production";
    private static final String PROTOCOL_HOST_SEPARATOR = "://";

    private String endpoint = DEFAULT_ENDPOINT;
    private String apiKey;
    private String releaseStage = DEFAULT_RELEASE_STAGE;
    private boolean sslEnabled = DEFAULT_USE_SSL;
    private Set<String> notifyReleaseStages = Sets.newHashSet();
    private Set<String> filters = Sets.newHashSet();
    private Set<String> projectPackages = Sets.newHashSet();
    private Set<String> ignoreClasses = Sets.newHashSet();
    private Optional<String> metaDataProviderClassName = Optional.absent();

    public String getEndpoint() {
        return endpoint;
    }

    public String getEndpointWithProtocol() {

        final String protocol;
        if (sslEnabled) {
            protocol = "https";
        } else {
            protocol = "http";
        }

        return protocol + PROTOCOL_HOST_SEPARATOR + endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getReleaseStage() {
        return releaseStage;
    }

    public void setReleaseStage(String releaseStage) {
        this.releaseStage = releaseStage;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public void setNotifyReleaseStages(Set<String> notifyReleaseStages) {
        this.notifyReleaseStages = notifyReleaseStages;
    }

    public void setFilters(Set<String> filters) {
        this.filters = filters;
    }

    public void setProjectPackages(Set<String> projectPackages) {
        this.projectPackages = projectPackages;
    }

    public void setIgnoreClasses(Set<String> ignoreClasses) {
        this.ignoreClasses = ignoreClasses;
    }

    public boolean hasMetaDataProvider() {
        return metaDataProviderClassName.isPresent();
    }

    public Optional<String> getMetaDataProviderClassName() {
        return metaDataProviderClassName;
    }

    public void setMetaDataProviderClassName(String metaDataProviderClassName) {
        this.metaDataProviderClassName = Optional.fromNullable(metaDataProviderClassName);
    }

    public boolean isStageIgnored() {

        if (notifyReleaseStages.isEmpty()) {
            return false;
        }

        return !notifyReleaseStages.contains(releaseStage);
    }

    public boolean isInProject(final String className) {

        for (final String packageName : projectPackages) {
            if (className.startsWith(packageName)) {
                return true;
            }
        }

        return false;
    }

    public boolean shouldNotifyFor(final String className) {
        return !ignoreClasses.contains(className);
    }

    public boolean isIgnoredByFilter(final String key) {
        return filters.contains(key);
    }

    public boolean isInvalid() {
        return isEndpointInvalid() ||
                isApiKeyInvalid() ||
                isReleaseStageInvalid() ||
                isMetaProviderClassNameInValid();
    }

    public boolean isEndpointInvalid() {
        return Strings.isNullOrEmpty(endpoint);
    }

    public boolean isApiKeyInvalid() {
        return Strings.isNullOrEmpty(apiKey);
    }

    public boolean isReleaseStageInvalid() {
        return Strings.isNullOrEmpty(releaseStage);
    }

    public boolean isMetaProviderClassNameInValid() {
        if (metaDataProviderClassName.isPresent()) {
            try {
                Class.forName(metaDataProviderClassName.get());
            } catch (final ClassNotFoundException e) {
                return true;
            }
        }

        return false;
    }

    public void addErrors(final ContextAware contextAware) {
        if (isEndpointInvalid()) {
            contextAware.addError("endpoint must not be null nor empty");
        }

        if (isApiKeyInvalid()) {
            contextAware.addError("apiKey must not be null nor empty");
        }

        if (isReleaseStageInvalid()) {
            contextAware.addError("releaseStage must not be null nor empty");
        }

        if (isMetaProviderClassNameInValid()) {
            contextAware.addError("Could not instantiate class: " + getMetaDataProviderClassName().get() + ". " +
                "Make sure that you provided the fully qualified class name and that the class has public access.");
        }
    }
}