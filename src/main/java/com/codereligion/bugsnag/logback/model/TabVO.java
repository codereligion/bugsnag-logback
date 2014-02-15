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
import java.util.Map;

public class TabVO {
    private final Map<String, Object> valuesByKeys = new HashMap<String, Object>();

    public TabVO add(final String key, final Object value) {
        valuesByKeys.put(key, value);
        return this;
    }

    public Map<String, Object> getValuesByKey() {
        return valuesByKeys;
    }
}
