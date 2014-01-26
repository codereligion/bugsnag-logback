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

import com.google.common.collect.Lists;
import java.util.List;

public class ExceptionVO {

    /**
     * The class of error that occurred. This field is used to group the
     * errors together so should not contain any contextual information
     * that would prevent correct grouping. This would ordinarily be the
     * Exception name when dealing with an exception.
     */
    private String errorClass;

    /**
     * The error message associated with the error. Usually this will
     * contain some information about this specific instance of the error
     * and is not used to group the errors (optional, default none).
     */
    private String message;

    /**
     * An array of stacktrace objects. Each object represents one line in
     * the exception's stacktrace. Bugsnag uses this information to help
     * with error grouping, as well as displaying it to the user.
     */
    private List<StackTraceVO> stacktrace = Lists.newArrayList();

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StackTraceVO> getStacktrace() {
        return stacktrace;
    }

    public void addStacktrace(List<StackTraceVO> stacktrace) {
        this.stacktrace.addAll(stacktrace);
    }
}
