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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GsonMessageBodyWriterTest {

    private Gson gson = new GsonBuilder().create();
    private GsonMessageBodyWriter bodyWriter = new GsonMessageBodyWriter(gson);

    @Test
    public void getSizeReturnsMinusOneAsSpecifiedInJAXRS20() {
        final long size = bodyWriter.getSize(null, null, null, null, null);
        assertThat(size, is(-1L));
    }

    @Test
    public void isWriteableSupportsAllTypes() {
        final boolean writeable = bodyWriter.isWriteable(null, null, null, null);
        assertThat(writeable, is(Boolean.TRUE));
    }
}
