package com.bugsnag.model;

import com.bugsnag.mock.business.User;
import org.junit.Test;
import static com.bugsnag.matcher.TabKeyValueMatcher.hasKeyValuePair;
import static org.hamcrest.MatcherAssert.assertThat;

public class TabVOTest {

    @Test
    public void allowsAddingOfKeyValuePairs() {
        final TabVO tabVO = new TabVO();
        tabVO.add("someKey", "someValue");

        assertThat(tabVO, hasKeyValuePair("someKey", "someValue"));
    }

    @Test
    public void allowsAddingComplexObjectsAsValues() {
        final TabVO tabVO = new TabVO();
        tabVO.add("someArray", new User("email", "password"));

        assertThat(tabVO, hasKeyValuePair("someArray", new User("email", "password")));
    }
}
