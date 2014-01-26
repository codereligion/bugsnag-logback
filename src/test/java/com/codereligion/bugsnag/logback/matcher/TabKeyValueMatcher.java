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
package com.codereligion.bugsnag.logback.matcher;

import com.codereligion.bugsnag.logback.model.TabVO;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class TabKeyValueMatcher extends TypeSafeMatcher<TabVO> {

    public static Matcher<TabVO> hasKeyValuePair(final String key, final Object value) {
        return new TabKeyValueMatcher(key, value);
    }

    private final String key;
    private final Object value;

    public TabKeyValueMatcher(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    protected boolean matchesSafely(final TabVO item) {
        return hasEntry(key, value).matches(item.getValuesByKey());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(" key: " + key + " and value: " + value);
    }
}
