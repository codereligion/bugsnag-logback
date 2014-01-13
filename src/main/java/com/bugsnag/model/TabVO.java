package com.bugsnag.model;

import java.util.HashMap;

public class TabVO {
    private final HashMap<String, Object> valuesByKeys = new HashMap<String, Object>();

    public TabVO add(final String key, final Object value) {
        valuesByKeys.put(key, value);
        return this;
    }

    public HashMap<String, Object> getValuesByKey() {
        return valuesByKeys;
    }
}
