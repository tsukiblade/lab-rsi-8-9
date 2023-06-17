/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class MessageService {
    private final Map<Long, Message> messages;
    private final Map<Long, Comment> comments;
    
    public MessageService() {
        this.messages = new HashMap<>();
        this.comments = new HashMap<>();
        Message m1 = new Message(1L, "First message", "Stefan");
        Message m2 = new Message(2L, "Second message", "Anna");
        Message m3 = new Message(3L, "Second message", "Szymon");    
    
        messages.put(1L, m1);
        messages.put(2L, m2);
        messages.put(3L, m3);
    }
    
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }
    
    public Message getMessage(Long id) {
        return messages.get(id);
    }
    
    public Message createMessage(Message message) {
        message.setId(messages.size() + 1L);
        messages.put(messages.size() + 1L, message);
        
        return message;
    }
    
    public Message deleteMessage(Long id) {
        return messages.remove(id);
    }
    
    public Message updateMessage(Message message) {
        messages.put(message.getId(), message);
        
        return message;
    }
    
    public Comment createComment(Comment comment, Long messageId) {
        comment.setId(comments.size() + 1L);
        comment.setMessageId(messageId);
        comments.put(comments.size() + 1L, comment);
        
        return comment;
    }
    
    public Comment deleteComment(Long id) {
        return comments.remove(id);
    }
    
    public Comment updateComment(Comment comment, Long messageId) {
        comment.setMessageId(messageId);
        comments.put(comment.getId(), comment);
        
        return comment;
    }
   
    public List<Comment> getComments(Long messageId) {
        ArrayList<Comment> searchedComments = new ArrayList<>();
        comments.forEach((id, comment) -> {
           if (comment == null) {
               return;
           }
           if (comment.getMessageId().equals(messageId)) {
               searchedComments.add(comment);
           } 
        });
        
        return searchedComments;
    }
}
