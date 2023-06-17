/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package resources;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author user
 */
@Path("messages")
public class MessagesResource {
    
    private static MessageService messageService = new MessageService();

    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;
    
    /**
     * Creates a new instance of MessagesResource
     */
    public MessagesResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    
    @Path("/{messageId}")
    public MessageResource getMessageResource(@PathParam("messageId") Long id) {
        Message message = messageService.getMessage(id);
        return new MessageResource(message, messageService);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage( Message message, @Context UriInfo uriInfo) {
   
        Message newMessage = messageService.createMessage(message);
        String newId = String.valueOf(newMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        Response response = Response.created(uri)
                .entity(newMessage)
                .build();
        
        return response;
    }
    
    @POST
    @Path("queryParams")
    @Produces(MediaType.TEXT_PLAIN)
    public String TestQuery(@QueryParam("value") String value) {
        return value;
    }
    
    @POST
    @Path("headerParams")
    @Produces(MediaType.TEXT_PLAIN)
    public String TestHeaders(@HeaderParam("value") String value) {
        return value;
    }
    
    @POST
    @Path("matrixParams")
    @Produces(MediaType.TEXT_PLAIN)
    public String TestMatrix(@MatrixParam("value") String value) {
        return value;
    }
    
    @POST
    @Path("uri")
    @Produces(MediaType.TEXT_PLAIN)
    public String TestUri() {
        return uriInfo.getAbsolutePath().toString();
    }
    
    @POST
    @Path("headers")
    @Produces(MediaType.TEXT_PLAIN)
    public String TestHeaders() {
        return headers.getRequestHeaders().toString();
    }
    
}
