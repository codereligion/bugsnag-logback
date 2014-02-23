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
import com.codereligion.bugsnag.logback.model.NotificationVO;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Converts {@link ILoggingEvent}s to {@link NotificationVO}s and sends them to the bugsnag api.
 *
 * @author Sebastian Gröbler
 */
public class Appender extends AppenderBase<ILoggingEvent> {

    private final Configuration configuration;
    private final Converter converter;
    private final Sender sender;

    /**
     * Constructors a new appender.
     */
    public Appender() {
        this(new Configuration(), new Sender());
    }

    Appender(final Configuration configuration, final Sender sender) {
        this.configuration = configuration;
        this.converter = new Converter(configuration);
        this.sender = sender;
    }

    @Override
    public void start() {
        if (configuration.isInvalid()) {
            configuration.addErrors(this);
            return;
        }

        sender.start(configuration, this);
        super.start();
    }

    @Override
    public void stop() {

        if (!isStarted()) {
            return;
        }

        sender.stop();
        super.stop();
    }

    @Override
    protected void append(final ILoggingEvent event) {

        if (!isStarted() || configuration.isStageIgnored() || shouldNotNotifyFor(event)) {
            return;
        }

        final NotificationVO notification = converter.convertToNotification(event);
        sender.send(notification);
    }

    /**
     * Sets the api key used for authentication and authorization with the bugsnag api.
     *
     * @param apiKey the api key
     */
    public void setApiKey(final String apiKey) {
        this.configuration.setApiKey(apiKey);
    }

    /**
     * Allows switching between http and https for communication with the bugsnag api.
     *
     * <p/>Optional, default: false.
     *
     * @param sslEnabled true for https, false for http
     */
    public void setSslEnabled(final boolean sslEnabled) {
        this.configuration.setSslEnabled(sslEnabled);
    }

    /**
     * Sets the bugsnag api endpoint's host name.
     *
     * <p/>Optional, default: notify.bugsnag.com
     *
     * @param endpoint the host name
     */
    public void setEndpoint(final String endpoint) {
        this.configuration.setEndpoint(endpoint);
    }

    /**
     * Sets the release stage used to check whether notification is enabled on this stage.
     *
     * <p/>Optional, default: production
     *
     * @param releaseStage the release stage
     */
    public void setReleaseStage(final String releaseStage) {
        this.configuration.setReleaseStage(releaseStage);
    }

    /**
     * Sets the notifiable release stages.
     *
     * <p/>Optional, default: production
     *
     * @param notifyReleaseStages a comma separated set of release stages
     */
    public void setNotifyReleaseStages(final String notifyReleaseStages) {
        this.configuration.setNotifyReleaseStages(splitByCommaToSet(notifyReleaseStages));
    }

    /**
     * Sets filters used to filter out unwanted key value pairs from data provided by the
     * specified {@link MetaDataProvider} implementation.
     *
     * <p/>Optional, default: empty
     *
     * @param filters a comma separated set of filter words
     */
    public void setFilters(final String filters) {
        this.configuration.setFilters(splitByCommaToSet(filters));
    }

    /**
     * Sets project packages used to highlight project specific lines in the exception's
     * stack trace.
     *
     * <p/>Optional, default: empty
     *
     * @param projectPackages a comma separated set of package names
     */
    public void setProjectPackages(final String projectPackages) {
        this.configuration.setProjectPackages(splitByCommaToSet(projectPackages));
    }

    /**
     * Sets ignored classes used to filter throwables which should not be send to the api.
     *
     * <p/>Optional, default: empty
     *
     * @param ignoreClasses a comma separated set of fully qualified class names
     */
    public void setIgnoreClasses(final String ignoreClasses) {
        this.configuration.setIgnoreClasses(splitByCommaToSet(ignoreClasses));
    }

    /**
     * Sets the {@link MetaDataProvider} implementation.
     *
     * <p/>Optional
     *
     * @param metaDataProviderClassName the fully qualified class name
     */
    public void setMetaDataProvider(final String metaDataProviderClassName) {
        this.configuration.setMetaDataProviderClassName(metaDataProviderClassName);
    }

    private Set<String> splitByCommaToSet(final String input) {
        return Sets.newHashSet(Splitter.on(",").trimResults().split(input));
    }

    private boolean shouldNotNotifyFor(final ILoggingEvent event) {
        return !containsException(event) || !configuration.shouldNotifyFor(event.getThrowableProxy().getClassName());
    }

    private boolean containsException(final ILoggingEvent event) {
        return event.getThrowableProxy() != null;
    }
}
