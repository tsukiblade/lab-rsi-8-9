/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.util.Date;

/**
 *
 * @author user
 */
public class Comment {
    private Long id;
    private String message;
    private Long messageId;
    
    public Comment() {
       
    }

    public Comment(Long id, String message, Long messageId, Date created, String author) {
        this.id = id;
        this.message = message;
        this.messageId = messageId;
        this.created = new Date();
        this.author = author;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    private Date created;
    private String author;
}
