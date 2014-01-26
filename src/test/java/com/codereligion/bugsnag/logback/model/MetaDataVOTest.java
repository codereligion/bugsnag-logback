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

import com.codereligion.bugsnag.logback.model.MetaDataVO;
import org.junit.Test;
import static com.codereligion.bugsnag.logback.matcher.TabKeyValueMatcher.hasKeyValuePair;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class MetaDataVOTest {

    private final MetaDataVO metaData = new MetaDataVO();

    @Test
    public void canAddMultipleKeyValuePairsForTheSameTab() {
        metaData.addToTab("User", "id", "someId");
        metaData.addToTab("User", "email", "someone@some.thing");

        assertThat(metaData.getTabsByName(), hasEntry(is("User"), hasKeyValuePair("id", "someId")));
        assertThat(metaData.getTabsByName(), hasEntry(is("User"), hasKeyValuePair("email", "someone@some.thing")));
    }

}
