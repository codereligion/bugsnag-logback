package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.codereligion.bugsnag.logback.model.MetaDataVO;

public class ExampleMetaDataProvider implements MetaDataProvider {

    @Override
    public MetaDataVO provide(ILoggingEvent loggingEvent) {
        return new MetaDataVO()
                .addToTab("Logging Details", "message", loggingEvent.getFormattedMessage())
                .addToTab("Logging Details", "level", loggingEvent.getLevel());
    }
}
