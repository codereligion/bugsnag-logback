package com.bugsnag.mock.model;

import com.bugsnag.model.MetaDataVO;

public class MockMetaDataVO extends MetaDataVO {

    public static MockMetaDataVO createMetaDataVO() {
        return new MockMetaDataVO();
    }

    public MockMetaDataVO add(final String tabName, final String key, final Object object) {
        this.addToTab(tabName, key, object);
        return this;
    }
}
