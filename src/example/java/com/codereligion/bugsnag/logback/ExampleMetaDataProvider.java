package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.codereligion.bugsnag.logback.model.MetaDataVO;
import java.util.Map;

public class ExampleMetaDataProvider implements MetaDataProvider {

    @Override
    public MetaDataVO provide(ILoggingEvent loggingEvent) {
        final Map<String,String> mdcMap = loggingEvent.getMDCPropertyMap();
        final Map<String, String> loggerContextMap = loggingEvent.getLoggerContextVO().getPropertyMap();

        return new MetaDataVO()
                // add some details about the logging event
                .addToTab("Logging Details", "message", loggingEvent.getFormattedMessage())
                .addToTab("Logging Details", "level", loggingEvent.getLevel())

                // add some information from the MDC
                .addToTab("User Details", "name", mdcMap.get("userName"))
                .addToTab("User Details", "email", mdcMap.get("email"))

                // add some information from the LoggerContext
                .addToTab("Application Details", "releaseDate", loggerContextMap.get("appReleaseDate"));
    }
}
