package com.bugsnag.model;

import java.util.HashMap;
import java.util.Map;

public class MetaDataVO {

    // TODO maybe split model into a a Map<String, Tab> where a tab is a Map<String, Object>
    private Map<String, Map<String, Object>> tabNameToKeyToObject = new HashMap<String, Map<String, Object>>();

    public void addToTab(final String tabName, final String key, final Object value) {
        final Map<String, Object> keyToObject = getAndEnsureTabExistence(tabName);
        keyToObject.put(key, value);
    }

    private Map<String, Object> getAndEnsureTabExistence(final String tabName) {
        final Map<String, Object> tab = tabNameToKeyToObject.get(tabName);
        final boolean tabDoesNotExist = tab == null;
        if (tabDoesNotExist) {
            final HashMap<String, Object> newTab = new HashMap<String, Object>();
            tabNameToKeyToObject.put(tabName, newTab);
            return newTab;
        }

        return tab;
    }

    public Map<String, Map<String, Object>> getTabNameToKeyToObject() {
        return tabNameToKeyToObject;
    }
}