package com.bugsnag;

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
    protected void append(final ILoggingEvent event) {

        if (!isStarted() || configuration.stageIsIgnored()) {
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

    public void setUseSSL(final boolean useSSL) {
        this.configuration.setUseSSL(useSSL);
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

    public void setAutoNotify(final boolean autoNotify) {
        this.configuration.setAutoNotify(autoNotify);
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
