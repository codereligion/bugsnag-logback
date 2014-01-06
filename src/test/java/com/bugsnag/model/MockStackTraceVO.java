package com.bugsnag.model;

public class MockStackTraceVO extends StackTraceVO {

    public static MockStackTraceVO createStackTraceVO() {
        return new MockStackTraceVO();
    }

    public MockStackTraceVO withFile(final String file) {
        setFile(file);
        return this;
    }

    public MockStackTraceVO withLineNumber(final int lineNumber) {
        setLineNumber(lineNumber);
        return this;
    }

    public MockStackTraceVO withMethod(final String method) {
        setMethod(method);
        return this;
    }

    public MockStackTraceVO whichIsInProject() {
        setInProject(true);
        return this;
    }

    public MockStackTraceVO whichIsNotInProject() {
        setInProject(false);
        return this;
    }
}
