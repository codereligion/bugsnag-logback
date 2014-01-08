package com.bugsnag;

import com.bugsnag.model.MetaDataVO;
import com.bugsnag.resource.MetaDataVOSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(MetaDataVO.class, new MetaDataVOSerializer())
            .create();

    public Gson getGson() {
        return gson;
    }
}
