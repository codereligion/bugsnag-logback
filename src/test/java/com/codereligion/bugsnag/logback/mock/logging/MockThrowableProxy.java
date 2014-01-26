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
package com.codereligion.bugsnag.logback.mock.logging;

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
