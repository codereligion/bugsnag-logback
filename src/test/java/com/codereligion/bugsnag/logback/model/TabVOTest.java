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

import com.codereligion.bugsnag.logback.mock.business.User;
import com.codereligion.bugsnag.logback.model.TabVO;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.matcher.TabKeyValueMatcher.hasKeyValuePair;
import static org.hamcrest.MatcherAssert.assertThat;

public class TabVOTest {

    @Test
    public void allowsAddingOfKeyValuePairs() {
        final TabVO tabVO = new TabVO();
        tabVO.add("someKey", "someValue");

        assertThat(tabVO, hasKeyValuePair("someKey", "someValue"));
    }

    @Test
    public void allowsAddingComplexObjectsAsValues() {
        final TabVO tabVO = new TabVO();
        tabVO.add("someArray", new User("email", "password"));

        assertThat(tabVO, hasKeyValuePair("someArray", new User("email", "password")));
    }
}
