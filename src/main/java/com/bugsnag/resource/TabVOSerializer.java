package com.bugsnag.resource;

import com.bugsnag.Configuration;
import com.bugsnag.model.TabVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;

public class TabVOSerializer implements JsonSerializer<TabVO> {

    private final Configuration configuration;

    public TabVOSerializer(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JsonElement serialize(final TabVO src, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonElement jsonElement = context.serialize(src.getValuesByKey());
        filterJsonElement(jsonElement);
        return jsonElement;
    }

    private void filterJsonElement(final JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            filterJsonArray(jsonElement.getAsJsonArray());
        } else if (jsonElement.isJsonObject()) {
            filterJsonObject(jsonElement.getAsJsonObject());
        }
    }

    private void filterJsonArray(final JsonArray jsonArray) {
        for (final JsonElement jsonElement : jsonArray) {
            filterJsonElement(jsonElement);
        }
    }

    private void filterJsonObject(final JsonObject jsonObject) {
        for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final String key = entry.getKey();
            if (configuration.isIgnoredByFilter(key)) {
                jsonObject.remove(key);
            } else {
                filterJsonElement(entry.getValue());
            }
        }
    }
}
