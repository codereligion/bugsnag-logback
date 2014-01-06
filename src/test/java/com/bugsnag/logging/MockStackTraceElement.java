package com.bugsnag.logging;

import ch.qos.logback.classic.spi.StackTraceElementProxy;

public class MockStackTraceElement {

    public static MockStackTraceElement createStackTraceElement() {
        return new MockStackTraceElement();
    }

    private String declaringClass;
    private String methodName;
    private String fileName;
    private int    lineNumber;

    public MockStackTraceElement withDeclaringClass(final String declaringClass) {
        this.declaringClass = declaringClass;
        return this;
    }

    public MockStackTraceElement withMethodName(final String methodName) {
        this.methodName = methodName;
        return this;
    }

    public MockStackTraceElement withFileName(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    public MockStackTraceElement withLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public StackTraceElementProxy toStacktStackTraceElementProxy() {
        return new StackTraceElementProxy(new StackTraceElement(declaringClass, methodName, fileName, lineNumber));
    }
}
