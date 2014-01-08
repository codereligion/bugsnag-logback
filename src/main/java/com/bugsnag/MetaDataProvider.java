package com.bugsnag;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.bugsnag.model.MetaDataVO;

public interface MetaDataProvider {

    MetaDataVO provide(ILoggingEvent loggingEvent);
}
