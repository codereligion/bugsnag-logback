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

public class StackTraceVO {

    /**
     * The file that this stack frame was executing.
     * It is recommended that you strip any unnecessary or common
     * information from the beginning of the path.
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
     * Is this stacktrace line is in the user's project code, set
     * this to true. It is useful for developers to be able to see
     * which lines of a stacktrace are within their own application,
     * and which are within third party libraries. This boolean field
     * allows Bugsnag to display this information in the stacktrace
     * as well as use the information to help group errors better.
     * (Optional, defaults to false).
     */
    private boolean inProject;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isInProject() {
        return inProject;
    }

    public void setInProject(boolean inProject) {
        this.inProject = inProject;
    }
}
