package com.bugsnag.integration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.bugsnag.MetaDataProvider;
import com.bugsnag.model.MetaDataVO;

public class CustomMetaDataProvider implements MetaDataProvider {

    @Override
    public MetaDataVO provide(final ILoggingEvent loggingEvent) {
        final MetaDataVO metaDataVO = new MetaDataVO();
        metaDataVO.addToTab("Logging", "level", loggingEvent.getLevel().toString());
        metaDataVO.addToTab("Logging", "message", loggingEvent.getMessage());
        return metaDataVO;
    }
}
