package com.bugsnag.model;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import static com.bugsnag.model.MetaDataVOTest.TabKeyValueMatcher.hasKeyValuePair;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

public class MetaDataVOTest {

    private final MetaDataVO metaData = new MetaDataVO();

    @Test
    public void canAddMultipleKeyValuePairsForTheSameTab() {
        metaData.addToTab("User", "id", "someId");
        metaData.addToTab("User", "email", "someone@some.thing");

        assertThat(metaData.getTabsByName(), hasEntry(is("User"), hasKeyValuePair("id", "someId")));
        assertThat(metaData.getTabsByName(), hasEntry(is("User"), hasKeyValuePair("email", "someone@some.thing")));
    }

    public static class TabKeyValueMatcher extends TypeSafeMatcher<TabVO> {

        public static Matcher<TabVO> hasKeyValuePair(final String key, final String value) {
            return new TabKeyValueMatcher(key, value);
        }

        private final String key;
        private final String value;

        public TabKeyValueMatcher(final String key, final String value) {
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
}
