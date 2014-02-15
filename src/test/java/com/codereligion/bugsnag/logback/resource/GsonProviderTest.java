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
import com.codereligion.bugsnag.logback.model.TabVO;
import com.google.gson.TypeAdapter;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class GsonProviderTest {

    private GsonFilterProvider gsonFilterProvider = mock(GsonFilterProvider.class);
    private GsonProvider gsonProvider = new GsonProvider(gsonFilterProvider);

    @Test
    public void registersAdapterForMetaDataVO() {
        final TypeAdapter<MetaDataVO> adapter = gsonProvider.getGson().getAdapter(MetaDataVO.class);

        assertThat(adapter, is(notNullValue()));
    }

    @Test
    public void registersAdapterForTabVO() {
        final TypeAdapter<TabVO> adapter = gsonProvider.getGson().getAdapter(TabVO.class);

        assertThat(adapter, is(notNullValue()));
    }
}
