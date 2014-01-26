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
import ch.qos.logback.core.AppenderBase;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import java.util.Set;

public class Appender extends AppenderBase<ILoggingEvent> {

    private final Configuration configuration;
    private final Sender sender;

    public Appender() {
        this(new Configuration(), new Sender());
    }

    Appender(final Configuration configuration, final Sender sender) {
        this.configuration = configuration;
        this.sender = sender;
    }

    @Override
    public void start() {
        if (configuration.isInvalid()) {
            configuration.addErrors(this);
        }

        sender.start(configuration, this);
        super.start();
    }

    @Override
    public void stop() {
        sender.stop();
        super.stop();
    }

    @Override
    protected void append(final ILoggingEvent event) {

        if (!isStarted() || configuration.isStageIgnored()) {
            return;
        }

        if (containsException(event) && shouldNotifyFor(event)) {
            sender.send(event);
        }
    }

    private boolean containsException(final ILoggingEvent event) {
        return event.getThrowableProxy() != null;
    }

    private boolean shouldNotifyFor(final ILoggingEvent event) {
        return configuration.shouldNotifyFor(event.getThrowableProxy().getClassName());
    }

    public void setSslEnabled(final boolean sslEnabled) {
        this.configuration.setSslEnabled(sslEnabled);
    }

    public void setEndpoint(final String endpoint) {
        this.configuration.setEndpoint(endpoint);
    }

    public void setApiKey(final String apiKey) {
        this.configuration.setApiKey(apiKey);
    }

    public void setReleaseStage(final String releaseStage) {
        this.configuration.setReleaseStage(releaseStage);
    }

    public void setNotifyReleaseStages(final String notifyReleaseStages) {
        this.configuration.setNotifyReleaseStages(splitByCommaToArray(notifyReleaseStages));
    }

    public void setFilters(final String filters) {
        this.configuration.setFilters(splitByCommaToArray(filters));
    }

    public void setProjectPackages(final String projectPackages) {
        this.configuration.setProjectPackages(splitByCommaToArray(projectPackages));
    }

    public void setIgnoreClasses(final String ignoreClasses) {
        this.configuration.setIgnoreClasses(splitByCommaToArray(ignoreClasses));
    }

    public void setMetaDataProvider(final String metaDataProviderClassName) {
        this.configuration.setMetaDataProviderClassName(metaDataProviderClassName);
    }

    private Set<String> splitByCommaToArray(final String input) {
        return Sets.newHashSet(Splitter.on(",").trimResults().split(input));
    }
}
