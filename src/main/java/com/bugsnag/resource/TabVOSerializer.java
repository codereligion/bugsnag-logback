package com.bugsnag.resource;

import com.bugsnag.model.TabVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class TabVOSerializer implements JsonSerializer<TabVO> {

    @Override
    public JsonElement serialize(final TabVO src, final Type typeOfSrc, final JsonSerializationContext context) {
        return context.serialize(src.getKeyToValue());
    }
}
