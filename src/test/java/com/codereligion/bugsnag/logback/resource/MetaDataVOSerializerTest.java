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
package com.codereligion.bugsnag.logback.resource;

import com.codereligion.bugsnag.logback.model.MetaDataVO;
import com.google.gson.JsonSerializationContext;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MetaDataVOSerializerTest {

    private MetaDataVOSerializer serializer = new MetaDataVOSerializer();

    @Test
    public void directlySerializesTheInternalMap() {

        // given
        final MetaDataVO metaData = new MetaDataVO();
        final JsonSerializationContext context = mock(JsonSerializationContext.class);

        // when
        serializer.serialize(metaData, null, context);

        // then
        verify(context).serialize(metaData.getTabsByName());
    }
}
