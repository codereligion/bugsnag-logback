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
package com.codereligion.bugsnag.logback.model;

/**
 * Represents one line in an exception's stack trace.
 *
 * @author Sebastian Gröbler
 */
public class StackTraceVO {

    /**
     * The file that this stack frame was executing.
     */
    private String file;

    /**
     * The line of the file that this frame of the stack was in.
     */
    private int lineNumber;

    /**
     * The method that this particular stack frame is within.
     */
    private String method;

    /**
     * True when this line is originated in the project's code.
     */
    private boolean inProject;

    public String getFile() {
        return file;
    }

    public void setFile(final String file) {
        this.file = file;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public boolean isInProject() {
        return inProject;
    }

    public void setInProject(final boolean inProject) {
        this.inProject = inProject;
    }
}
