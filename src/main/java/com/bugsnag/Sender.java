package com.bugsnag;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.ContextAware;
import com.bugsnag.model.NotificationVO;
import com.bugsnag.resource.GsonMessageBodyReader;
import com.bugsnag.resource.GsonMessageBodyWriter;
import com.bugsnag.resource.NotifierResource;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

// TODO do not initialize client with every send
class Sender {

    private Configuration configuration;
    private ContextAware contextAware;
    private Converter converter;
    private GsonProvider gsonProvider;

    public Sender() {
        this(new GsonProvider());
    }

    Sender(final GsonProvider gsonProvider) {
        this.gsonProvider = gsonProvider;
    }

    public void start(final Configuration configuration,  final ContextAware contextAware) {
        start(configuration, contextAware, new Converter(configuration));
    }

    void start(final Configuration configuration, final ContextAware contextAware, final Converter converter) {
        this.configuration = configuration;
        this.contextAware = contextAware;
        this.converter = converter;

    }

    private Client createClient() {
        final Client client = ClientBuilder.newClient();
        final Gson gson = gsonProvider.getGson();

        client.register(new GsonMessageBodyReader(gson));
        client.register(new GsonMessageBodyWriter(gson));

        return client;
    }

    public void send(final ILoggingEvent event) {
        Client client = null;

        try {
            client = createClient();
            final ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget) client.target(configuration.getEndpointWithProtocol());
            final NotifierResource notifierResource= resteasyWebTarget.proxy(NotifierResource.class);
            final NotificationVO notification = converter.convertToNotification(event);
            final Response response = notifierResource.sendNotification(notification);
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
            if (client != null) {
                client.close();
            }
        }
    }
}
