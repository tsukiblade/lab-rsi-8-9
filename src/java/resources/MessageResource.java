/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author user
 */
public class MessageResource {
    private Message message;
    private MessageService messageService;
    
    public MessageResource(Message message, MessageService messageService) {
        this.message = message;
        this.messageService = messageService;
    }
    
    @GET
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAllComments() {
        return messageService.getComments(message.getId());
    }
    
    @POST
    @Path("/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment( Comment comment, @Context UriInfo uriInfo) {
   
        Comment newComment = messageService.createComment(comment, message.getId());
        String newId = String.valueOf(newComment.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        Response response = Response.created(uri)
                .entity(newComment)
                .build();
        
        return response;
    }
    
    @PUT
    @Path("/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment updateComment(Comment comment) {
        return messageService.updateComment(comment, message.getId());
    }

    @DELETE
    @Path("/comments/{commentId}")
    public void deleteComment(@PathParam("commentId") Long id) {
        messageService.deleteMessage(id);
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(Message message) {
        return messageService.updateMessage(message);
    }

    @DELETE
    public void deleteMessage(@PathParam("messageId") Long id) {
        messageService.deleteMessage(id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("messageId") Long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(id);
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessagesResource.class)
                .path(String.valueOf(message.getId()))
                .build()
                .toString();
        
        String uri2 = uriInfo.getBaseUriBuilder()
                .path(MessagesResource.class)
                .path(MessagesResource.class, "getMessageResource")
                .path(MessagesResource.class)
                .resolveTemplate("messageId", message.getId())
                .build()
                .toString();
        
        message.getLinks().add(new Link(uri, "self"));
        message.getLinks().add(new Link(uri2, "comments"));
        
        return message;
    }
}
