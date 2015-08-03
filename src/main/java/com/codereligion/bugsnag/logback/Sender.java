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

import ch.qos.logback.core.spi.ContextAware;
import com.codereligion.bugsnag.logback.model.NotificationVO;
import com.codereligion.bugsnag.logback.resource.GsonMessageBodyWriter;
import com.codereligion.bugsnag.logback.resource.GsonProvider;
import com.codereligion.bugsnag.logback.resource.NotifierResource;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * Sends {@link NotificationVO}s to the bugsnag endpoint.
 *
 * @author Sebastian Gröbler
 */
public class Sender {

    /**
     * All known and expected status codes of the bugsnag endpoint.
     */
    public static final class StatusCode {
        public static final int OK = 200;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int REQUEST_ENTITY_TOO_LARGE = 413;
        public static final int TOO_MANY_REQUESTS = 429;
    }

    private String endpoint;
    private ContextAware contextAware;
    private GsonProvider gsonProvider;
    private Client client;
    private boolean started;

    /**
     * Starts the sender with the given {@code configuration} and {@code contextAware}.
     *
     * @param configuration the {@link Configuration} to apply
     * @param contextAware the {@link ContextAware} to use for error reporting
     */
    public void start(final Configuration configuration, final ContextAware contextAware) {
        this.endpoint = configuration.getEndpointWithProtocol();
        this.contextAware = contextAware;
        this.gsonProvider = new GsonProvider(configuration);
        this.client = createClient();
        this.started = true;
    }

    /**
     * @return true when this sender was started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Stops this sender and closes all current connections/streams.
     */
    public void stop() {
        client.close();
        this.started = false;
    }

    /**
     * @return true when this sender was not yet started or was stopped
     */
    public boolean isStopped() {
        return !isStarted();
    }

    /**
     * Sends the given {@code notification}, but no-ops when the sender was not yet started.
     *
     * @param notification the {@link NotificationVO} to send
     */
    public void send(final NotificationVO notification) {

        if (isStopped()) {
            return;
        }

        Response response = null;

        try {
            final ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client.target(endpoint);
            final NotifierResource notifierResource= resteasyWebTarget.proxy(NotifierResource.class);
            response = notifierResource.sendNotification(notification);
            final int statusCode = response.getStatus();

            final boolean isOk = StatusCode.OK == statusCode;

            if (isOk) {
                contextAware.addInfo("Successfully delivered notification to bugsnag.");
            } else if (isExpectedErrorCode(statusCode)) {
                contextAware.addError("Could not deliver notification to bugsnag, got http status code: " + statusCode);
            } else {
                contextAware.addError("Unexpected http status code: " + statusCode);
            }

        } catch (final Throwable throwable) {
            contextAware.addError("Could not deliver notification, unexpected exception occurred.", throwable);
        }

        if (response != null) {
            response.close();
        }
    }

    private boolean isExpectedErrorCode(final int statusCode) {
        return statusCode == StatusCode.BAD_REQUEST ||
                statusCode == StatusCode.UNAUTHORIZED ||
                statusCode == StatusCode.REQUEST_ENTITY_TOO_LARGE ||
                statusCode == StatusCode.TOO_MANY_REQUESTS;
    }

    private Client createClient() {
        final Gson gson = gsonProvider.getGson();

        ResteasyClientBuilder clientBuilder = new ResteasyClientBuilder().connectionPoolSize(100);
        return clientBuilder
            .build()
            .register(new GsonMessageBodyWriter(gson));
    }
}
