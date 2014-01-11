package com.bugsnag.resource;

import com.bugsnag.Configuration;
import com.bugsnag.model.MetaDataVO;
import com.bugsnag.model.TabVO;
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
