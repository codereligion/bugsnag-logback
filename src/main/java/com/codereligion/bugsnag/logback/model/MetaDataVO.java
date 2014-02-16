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

/**
 * Represents the bugsnag meta data structure which resembles this json structure:
 *
 * <pre>
 *     {
 *         "anyTabName": {
 *             "anyKeyName": "someValue",
 *             "anyOtherKeyName": {
 *                 "nestedKeyName": "someValue"
 *             }
 *         },
 *         "anyOtherTabName": {
 *             "anyKeyName": "someValue"
 *         }
 *     }
 *
 * </pre>
 *
 * @author Sebastian Gröbler
 */
public class MetaDataVO {

    private Map<String, TabVO> tabsByName = new HashMap<String, TabVO>();

    /**
     * Adds the given {@code key}/{@code value} pair to the specified {@code tabName}.
     *
     * @param tabName the name of the tab to add the key/value pair
     * @param key the key to map the {@code value} to
     * @param value the value for the given {@code key}
     * @return a reference of this object
     */
    public MetaDataVO addToTab(final String tabName, final String key, final Object value) {
        getAndEnsureTabExistence(tabName).add(key, value);
        return this;
    }

    /**
     * The underlying data structure of this object.
     *
     * @return a map of string to {@link TabVO}
     */
    public Map<String, TabVO> getTabsByName() {
        return tabsByName;
    }

    private TabVO getAndEnsureTabExistence(final String tabName) {
        final TabVO tab = tabsByName.get(tabName);
        final boolean tabDoesNotExist = tab == null;
        if (tabDoesNotExist) {
            final TabVO newTab = new TabVO();
            tabsByName.put(tabName, newTab);
            return newTab;
        }

        return tab;
    }
}