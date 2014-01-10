package com.bugsnag.model;

import java.util.HashMap;

public class TabVO {
    private final HashMap<String, Object> keyToValue = new HashMap<String, Object>();

    public void add(final String key, final Object value) {
        keyToValue.put(key, value);
    }

    public HashMap<String, Object> getKeyToValue() {
        return keyToValue;
    }
}
