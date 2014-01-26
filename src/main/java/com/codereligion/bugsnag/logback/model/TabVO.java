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
