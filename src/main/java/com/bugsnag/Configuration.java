package com.bugsnag;

import ch.qos.logback.core.spi.ContextAware;
import com.google.common.collect.Sets;
import java.util.Set;

public class Configuration {
    public static final String DEFAULT_ENDPOINT = "notify.bugsnag.com";
    public static final boolean DEFAULT_AUTO_NOTIFY = true;
    public static final boolean DEFAULT_USE_SSL = false;
    public static final String PROTOCOL_HOST_SEPARATOR = "://";
    public static final String DEFAULT_RELEASE_STAGE = "production";

    private String endpoint = DEFAULT_ENDPOINT;
    private String apiKey;
    private String releaseStage = DEFAULT_RELEASE_STAGE;
    private boolean autoNotify = DEFAULT_AUTO_NOTIFY;
    private boolean useSSL = DEFAULT_USE_SSL;
    private Set<String> notifyReleaseStages = Sets.newHashSet();
    private Set<String> filters = Sets.newHashSet();
    private Set<String> projectPackages = Sets.newHashSet();
    private Set<String> ignoreClasses = Sets.newHashSet();
    private String metaDataProviderClassName;

    public String getEndpoint() {
        return endpoint;
    }

    public String getEndpointWithProtocol() {

        final String protocol;
        if (useSSL) {
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

    public boolean isAutoNotify() {
        return autoNotify;
    }

    public void setAutoNotify(boolean autoNotify) {
        this.autoNotify = autoNotify;
    }

    public boolean useSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public Set<String> getNotifyReleaseStages() {
        return notifyReleaseStages;
    }

    public void setNotifyReleaseStages(Set<String> notifyReleaseStages) {
        this.notifyReleaseStages = notifyReleaseStages;
    }

    public Set<String> getFilters() {
        return filters;
    }

    public void setFilters(Set<String> filters) {
        this.filters = filters;
    }

    public Set<String> getProjectPackages() {
        return projectPackages;
    }

    public void setProjectPackages(Set<String> projectPackages) {
        this.projectPackages = projectPackages;
    }

    public Set<String> getIgnoreClasses() {
        return ignoreClasses;
    }

    public void setIgnoreClasses(Set<String> ignoreClasses) {
        this.ignoreClasses = ignoreClasses;
    }

    public boolean hasMetaDataProvider() {
        return getMetaDataProviderClassName() != null;
    }

    public String getMetaDataProviderClassName() {
        return metaDataProviderClassName;
    }

    public void setMetaDataProviderClassName(String metaDataProviderClassName) {
        this.metaDataProviderClassName = metaDataProviderClassName;
    }

    public boolean stageIsIgnored() {

        if (getNotifyReleaseStages().isEmpty()) {
            return false;
        }

        return !getNotifyReleaseStages().contains(getReleaseStage());
    }

    public boolean shouldNotifyFor(final String className) {
        return !getIgnoreClasses().contains(className);
    }

    public boolean isIgnoredByFilter(final String key) {
        return getFilters().contains(key);
    }

    public boolean isInvalid() {
        // TODO
        return true;
    }

    public void addErrors(ContextAware contextAware) {
        // TODO
    }
}