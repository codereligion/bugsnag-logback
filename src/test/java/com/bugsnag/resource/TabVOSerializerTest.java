package com.bugsnag.resource;

import com.bugsnag.Configuration;
import com.bugsnag.model.TabVO;
import com.bugsnag.model.User;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class TabVOSerializerTest {


    @Test
    public void serializesMapDirectly() {
        final Gson gson = createGsonWithFilterConfiguration();

        final TabVO tab = new TabVO().add("user", "someUserName");
        final Map<String, String> map = Collections.singletonMap("user", "someUserName");

        final String tabJson = gson.toJson(tab);
        final String mapJson = gson.toJson(map);

        assertThat(tabJson, is(mapJson));
    }

    @Test
    public void filtersOutIgnoredKeyInObject() {
        final Gson gson = createGsonWithFilterConfiguration("password");

        final TabVO tab = new TabVO()
                .add("email", "foo@bar.com")
                .add("password", "v3rys3cr3t");

        final String jsonString = gson.toJson(tab);

        assertThat(jsonString, not(containsString("password")));
        assertThat(jsonString, not(containsString("v3rys3cr3t")));
    }

    @Test
    public void filtersOutIgnoredKeyInNestedObjects() {
        final Gson gson = createGsonWithFilterConfiguration("password");

        final User user = new User("foo@bar.com", "v3rys3cr3t");
        final TabVO tab = new TabVO().add("user", user);

        final String jsonString = gson.toJson(tab);

        assertThat(jsonString, not(containsString("password")));
        assertThat(jsonString, not(containsString("v3rys3cr3t")));
    }

    @Test
    public void filtersOutIgnoredKeyInarraysObjects() {
        final Gson gson = createGsonWithFilterConfiguration("password");

        final User[] users = {new User("foo@bar.com", "v3rys3cr3t")};
        final TabVO tab = new TabVO().add("users", users);

        final String jsonString = gson.toJson(tab);

        assertThat(jsonString, not(containsString("password")));
        assertThat(jsonString, not(containsString("v3rys3cr3t")));
    }

    private Gson createGsonWithFilterConfiguration(final String... filters) {
        final Configuration configuration = new Configuration();
        configuration.setFilters(Sets.newHashSet(filters));

        return new GsonBuilder()
                .registerTypeAdapter(TabVO.class, new TabVOSerializer(configuration))
                .create();
    }

}
