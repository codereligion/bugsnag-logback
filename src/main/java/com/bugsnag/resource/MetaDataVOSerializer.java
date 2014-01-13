package com.bugsnag.resource;

import com.bugsnag.model.MetaDataVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class MetaDataVOSerializer implements JsonSerializer<MetaDataVO> {

    @Override
    public JsonElement serialize(final MetaDataVO src, final Type typeOfSrc, final JsonSerializationContext context) {
        return context.serialize(src.getTabsByName());
    }
}
