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
import ch.qos.logback.core.spi.ContextAware;
import com.codereligion.bugsnag.logback.model.NotificationVO;
import com.codereligion.bugsnag.logback.resource.GsonMessageBodyReader;
import com.codereligion.bugsnag.logback.resource.GsonMessageBodyWriter;
import com.codereligion.bugsnag.logback.resource.GsonProvider;
import com.codereligion.bugsnag.logback.resource.NotifierResource;
import com.codereligion.bugsnag.logback.resource.GsonProvider;
import com.codereligion.bugsnag.logback.resource.NotifierResource;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class Sender {

    private Configuration configuration;
    private ContextAware contextAware;
    private Converter converter;
    private GsonProvider gsonProvider;
    private Client client;
    private boolean started;

    public void start(final Configuration configuration,  final ContextAware contextAware) {
        this.configuration = configuration;
        this.contextAware = contextAware;
        this.converter = new Converter(configuration);
        this.gsonProvider = new GsonProvider(configuration);
        this.client = createClient();
        this.started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isStopped() {
        return !isStarted();
    }

    public void send(final ILoggingEvent event) {

        if (isStopped()) {
            return;
        }

        Response response = null;

        try {
            final ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client.target(configuration.getEndpointWithProtocol());
            final NotifierResource notifierResource= resteasyWebTarget.proxy(NotifierResource.class);
            final NotificationVO notification = converter.convertToNotification(event);
            response = notifierResource.sendNotification(notification);
            final Response.StatusType statusInfo = response.getStatusInfo();

            switch(statusInfo.getStatusCode()) {
                case 200: contextAware.addInfo("Successfully delivered notification to bugsnag."); break;
                case 400: // FALL THROUGH
                case 401: // FALL THROUGH
                case 413: // FALL THROUGH
                case 429: contextAware.addError("Error: " + statusInfo); break;
                default: contextAware.addError("Unexpected response code:" + statusInfo);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public void stop() {
        client.close();
        this.started = false;
    }

    private Client createClient() {
        final Client client = ClientBuilder.newClient();
        final Gson gson = gsonProvider.getGson();

        client.register(new GsonMessageBodyReader(gson));
        client.register(new GsonMessageBodyWriter(gson));

        return client;
    }
}
