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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Provides a {@link Gson} instance to serializes data with.
 *
 * @author Sebastian Gröbler
 */
public class GsonProvider {

    private final Gson gson;

    /**
     * Creates a new instance with the given {@code jsonFilterProvider}.
     *
     * @param jsonFilterProvider the {@link JsonFilterProvider} implementation to use
     */
    public GsonProvider(final JsonFilterProvider jsonFilterProvider) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(MetaDataVO.class, new MetaDataVOSerializer())
                .registerTypeAdapter(TabVO.class, new TabVOSerializer(jsonFilterProvider))
                .create();
    }

    /**
     * @return a fully configured {@link Gson} instance
     */
    public Gson getGson() {
        return gson;
    }
}
