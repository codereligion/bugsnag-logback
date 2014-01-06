package com.bugsnag.model;

public class MockEventVO extends EventVO {

    public static MockEventVO createEventVO() {
        return new MockEventVO();
    }

    public MockEventVO withReleaseStage(final String releaseStage) {
        setReleaseStage(releaseStage);
        return this;
    }

    public MockEventVO add(final ExceptionVO exceptionVO) {
        getExceptions().add(exceptionVO);
        return this;
    }

    public MockEventVO withUserId(final String userId) {
        setUserId(userId);
        return this;
    }

    public MockEventVO withAppVersion(final String appVersion) {
        setAppVersion(appVersion);
        return this;
    }

    public MockEventVO withOsVersion(final String osVersion) {
        setOsVersion(osVersion);
        return this;
    }

    public MockEventVO withContext(final String context) {
        setContext(context);
        return this;
    }

    public MockEventVO withGroupingHash(final String groupingHash) {
        setGroupingHash(groupingHash);
        return this;
    }
}
