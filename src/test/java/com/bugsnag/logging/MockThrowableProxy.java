package com.bugsnag.logging;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.google.common.collect.Lists;
import java.util.List;

public class MockThrowableProxy implements IThrowableProxy {
    private String message;
    private String className;
    private StackTraceElementProxy[] stackTraceElements = new StackTraceElementProxy[0];
    private int commonFrames;
    private IThrowableProxy cause;
    private IThrowableProxy[] suppressed;

    public static MockThrowableProxy createThrowableProxy() {
        return new MockThrowableProxy();
    }

    public MockThrowableProxy withMessage(final String message) {
        this.message = message;
        return this;
    }

    public MockThrowableProxy withClassName(final String className) {
        this.className = className;
        return this;
    }

    public MockThrowableProxy addStackTraceElementProxy(final MockStackTraceElement stackTraceElement) {
        final List<StackTraceElementProxy> list = Lists.newArrayList(stackTraceElements);
        list.add(stackTraceElement.toStacktStackTraceElementProxy());
        stackTraceElements = list.toArray(stackTraceElements);
        return this;
    }

    public MockThrowableProxy withCommonFrames(final int commonFrames) {
        this.commonFrames = commonFrames;
        return this;
    }

    public MockThrowableProxy withCause(final IThrowableProxy cause) {
        this.cause = cause;
        return this;
    }

    public MockThrowableProxy withSuppressed(final IThrowableProxy[] suppressed) {
        this.suppressed = suppressed;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public StackTraceElementProxy[] getStackTraceElementProxyArray() {
        return stackTraceElements;
    }

    @Override
    public int getCommonFrames() {
        return commonFrames;
    }

    @Override
    public IThrowableProxy getCause() {
        return cause;
    }

    @Override
    public IThrowableProxy[] getSuppressed() {
        return suppressed;
    }
}
