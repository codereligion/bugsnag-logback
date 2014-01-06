package com.bugsnag.model;

public class NotifierVO {

    private static final String NOTIFIER_NAME = "Java Logback Notifier";
    private static final String NOTIFIER_VERSION = "1.0.0";
    private static final String NOTIFIER_URL = "https://bugsnag.com";

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
}
