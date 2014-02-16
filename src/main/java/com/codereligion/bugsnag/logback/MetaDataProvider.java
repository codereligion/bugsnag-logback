/**
 * Copyright 2014 www.codereligion.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.codereligion.bugsnag.logback.model.MetaDataVO;

/**
 * Allows to add meta for each notification.
 *
 * @author Sebastian Gröbler
 */
public interface MetaDataProvider {

    /**
     * Provides an instance of {@link MetaDataVO}.
     *
     * @param loggingEvent implementation of {@link ILoggingEvent}, cannot be {@code null}
     * @return an instance of {@link MetaDataVO} or {@code null}
     */
    MetaDataVO provide(ILoggingEvent loggingEvent);
}
