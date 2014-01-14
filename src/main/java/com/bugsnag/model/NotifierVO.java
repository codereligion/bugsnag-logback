package com.bugsnag.model;

public class NotifierVO {

    private static final String NOTIFIER_NAME = "Bugsnag Logback Notifier";
    private static final String NOTIFIER_VERSION = "1.0.0";
    private static final String NOTIFIER_URL = "https://github.com/sierragolf/bugsnag-logback";

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
