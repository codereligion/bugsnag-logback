package com.bugsnag.model;

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
