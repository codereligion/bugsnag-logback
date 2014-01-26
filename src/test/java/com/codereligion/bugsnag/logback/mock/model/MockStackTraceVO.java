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
package com.codereligion.bugsnag.logback.mock.model;

import com.codereligion.bugsnag.logback.model.StackTraceVO;

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
