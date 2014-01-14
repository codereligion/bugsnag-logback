package com.bugsnag.matcher;

import com.bugsnag.model.TabVO;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class TabKeyValueMatcher extends TypeSafeMatcher<TabVO> {

    public static Matcher<TabVO> hasKeyValuePair(final String key, final Object value) {
        return new TabKeyValueMatcher(key, value);
    }

    private final String key;
    private final Object value;

    public TabKeyValueMatcher(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    protected boolean matchesSafely(final TabVO item) {
        return hasEntry(key, value).matches(item.getValuesByKey());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(" key: " + key + " and value: " + value);
    }
}
