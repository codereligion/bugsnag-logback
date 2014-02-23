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
import com.codereligion.bugsnag.logback.resource.JsonFilterProvider;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * Holds all configurable elements of this appender.
 *
 * @author Sebastian Gröbler
 */
public class Configuration implements JsonFilterProvider {

    private static final String DEFAULT_ENDPOINT = "notify.bugsnag.com";
    private static final boolean DEFAULT_USE_SSL = false;
    private static final String DEFAULT_RELEASE_STAGE = "production";
    private static final String PROTOCOL_HOST_SEPARATOR = "://";
    private static final String HTTPS = "https";
    private static final String HTTP = "http";

    private String endpoint = DEFAULT_ENDPOINT;
    private String apiKey;
    private String releaseStage = DEFAULT_RELEASE_STAGE;
    private boolean sslEnabled = DEFAULT_USE_SSL;
    private Set<String> notifyReleaseStages = Sets.newHashSet(DEFAULT_RELEASE_STAGE);
    private Set<String> filters = Sets.newHashSet();
    private Set<String> projectPackages = Sets.newHashSet();
    private Set<String> ignoreClasses = Sets.newHashSet();
    private Optional<String> metaDataProviderClassName = Optional.absent();

    /**
     * @return the full url of the endpoint
     */
    public String getEndpointWithProtocol() {

        final String protocol;
        if (sslEnabled) {
            protocol = HTTPS;
        } else {
            protocol = HTTP;
        }

        return protocol + PROTOCOL_HOST_SEPARATOR + endpoint;
    }

    /**
     * @see Appender#setEndpoint(String)
     * @param endpoint the host name of the endpoint
     */
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @see Appender#setApiKey(String)
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @see Appender#setApiKey(String)
     * @param apiKey the api key
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @see Appender#setReleaseStage(String)
     * @return the release stage
     */
    public String getReleaseStage() {
        return releaseStage;
    }

    /**
     * @see Appender#setReleaseStage(String)
     * @param releaseStage the release stage
     */
    public void setReleaseStage(final String releaseStage) {
        this.releaseStage = releaseStage;
    }

    /**
     * @see Appender#setSslEnabled(boolean)
     * @return true when https is used, false when http is used
     */
    public boolean isSslEnabled() {
        return sslEnabled;
    }

    /**
     * @see Appender#setSslEnabled(boolean)
     * @param sslEnabled true to enabled https, false for http
     */
    public void setSslEnabled(final boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    /**
     * @see Appender#setNotifyReleaseStages(String)
     * @param notifyReleaseStages the notifiable release stages
     */
    public void setNotifyReleaseStages(final Set<String> notifyReleaseStages) {
        this.notifyReleaseStages = notifyReleaseStages;
    }

    /**
     * @see Appender#setFilters(String)
     * @param filters the meta data filters
     */
    public void setFilters(final Set<String> filters) {
        this.filters = filters;
    }

    /**
     * @see Appender#setProjectPackages(String)
     * @param projectPackages the project packages
     */
    public void setProjectPackages(final Set<String> projectPackages) {
        this.projectPackages = projectPackages;
    }

    /**
     * @see Appender#setIgnoreClasses(String)
     * @param ignoreClasses the ignored throwable classes
     */
    public void setIgnoreClasses(final Set<String> ignoreClasses) {
        this.ignoreClasses = ignoreClasses;
    }

    /**
     * @return true when a {@link MetaDataProvider} class name was specified
     */
    public boolean hasMetaDataProvider() {
        return metaDataProviderClassName.isPresent();
    }

    /**
     * @return the optional of the {@link MetaDataProvider} class name
     */
    public Optional<String> getMetaDataProviderClassName() {
        return metaDataProviderClassName;
    }

    /**
     * @param metaDataProviderClassName the fully qualified class name of a {@link MetaDataProvider}
     */
    public void setMetaDataProviderClassName(final String metaDataProviderClassName) {
        this.metaDataProviderClassName = Optional.fromNullable(metaDataProviderClassName);
    }

    /**
     * @return true when the specified {@code releaseStage} is not included in the specified
     * {@code notifyReleaseStages}.
     */
    public boolean isStageIgnored() {
        return !notifyReleaseStages.contains(releaseStage);
    }

    /**
     * @param className the fully qualified class name to check
     * @return true when it starts with any of the specified {@code projectPackages}
     */
    public boolean isInProject(final String className) {

        for (final String packageName : projectPackages) {
            if (className.startsWith(packageName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param className the fully qualified class name to check
     * @return true when the name of the throwable class is being ignored by the specified {@code ignoreClasses}
     */
    public boolean shouldNotifyFor(final String className) {
        return !ignoreClasses.contains(className);
    }

    /**
     * @param key the key to check
     * @return true when the given key is contained in the specified {@code filters}
     */
    public boolean isIgnoredByFilter(final String key) {
        return filters.contains(key);
    }

    /**
     * @return true when all fields are valid
     */
    public boolean isInvalid() {
        return isEndpointInvalid() ||
                isApiKeyInvalid() ||
                isReleaseStageInvalid() ||
                isMetaProviderClassNameInValid();
    }

    /**
     * @param contextAware the {@link ContextAware} implementation to which potential errors will be added
     */
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
                    "Make sure that you provided the fully qualified class name and that the class has a public " +
                    "accessible default constructor.");
        }
    }

    private boolean isEndpointInvalid() {
        return Strings.isNullOrEmpty(endpoint);
    }

    private boolean isApiKeyInvalid() {
        return Strings.isNullOrEmpty(apiKey);
    }

    private boolean isReleaseStageInvalid() {
        return Strings.isNullOrEmpty(releaseStage);
    }

    private boolean isMetaProviderClassNameInValid() {
        if (metaDataProviderClassName.isPresent()) {
            try {
                final Class<?> metaDataProviderClass = Class.forName(metaDataProviderClassName.get());
                final Constructor<?>[] constructors = metaDataProviderClass.getConstructors();
                final boolean hasNoPublicConstructors = constructors.length == 0;
                final boolean hasNoDefaultConstructor = !containsDefaultConstructor(constructors);
                final boolean doesNotImplementMetaDataProvider = !MetaDataProvider.class.isAssignableFrom(metaDataProviderClass);

                return hasNoPublicConstructors || hasNoDefaultConstructor || doesNotImplementMetaDataProvider;

            } catch (final ClassNotFoundException e) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDefaultConstructor(final Constructor<?>[] constructors) {
        for (final Constructor<?> constructor : constructors) {
            final boolean noParameters = constructor.getParameterTypes().length == 0;
            if (noParameters) {
                return true;
            }
        }

        return false;
    }
}