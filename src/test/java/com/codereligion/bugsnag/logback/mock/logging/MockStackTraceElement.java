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
