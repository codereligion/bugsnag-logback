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

    @Override
    public int hashCode() {
        return valuesByKeys.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabVO tabVO = (TabVO) o;

        if (!valuesByKeys.equals(tabVO.valuesByKeys)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "TabVO{" +
                "valuesByKeys=" + valuesByKeys +
                '}';
    }
}
