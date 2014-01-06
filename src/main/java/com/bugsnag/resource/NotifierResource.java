package com.bugsnag.resource;

import com.bugsnag.model.NotificationVO;
import javax.ws.rs.Consumes;
import javax.ws.rs  .POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface NotifierResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response sendNotification(NotificationVO notificationVO);
}