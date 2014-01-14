package com.bugsnag.model;

import org.junit.Test;
import static com.bugsnag.matcher.TabKeyValueMatcher.hasKeyValuePair;
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

}
