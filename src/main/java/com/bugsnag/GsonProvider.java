package com.bugsnag;

import com.bugsnag.model.MetaDataVO;
import com.bugsnag.model.TabVO;
import com.bugsnag.resource.MetaDataVOSerializer;
import com.bugsnag.resource.TabVOSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private final Gson gson;

    public GsonProvider(final Configuration configuration) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(MetaDataVO.class, new MetaDataVOSerializer())
                .registerTypeAdapter(TabVO.class, new TabVOSerializer(configuration))
                .create();
    }

    public Gson getGson() {
        return gson;
    }
}
