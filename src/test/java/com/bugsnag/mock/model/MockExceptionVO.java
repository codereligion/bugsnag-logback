package com.bugsnag.mock.model;

import com.bugsnag.model.ExceptionVO;
import com.bugsnag.model.StackTraceVO;

public class MockExceptionVO extends ExceptionVO {

    public static MockExceptionVO createExceptionVO() {
        return new MockExceptionVO();
    }

    public MockExceptionVO withErrorClass(final String errorClass) {
        setErrorClass(errorClass);
        return this;
    }

    public MockExceptionVO withMessage(final String message) {
        setMessage(message);
        return this;
    }

    public MockExceptionVO add(final StackTraceVO stackTraceVO) {
        getStacktrace().add(stackTraceVO);
        return this;
    }
}
