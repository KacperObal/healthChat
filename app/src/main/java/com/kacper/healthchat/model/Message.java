package com.kacper.healthchat.model;

import java.util.Date;

/**
 * Created by Kacper on 18.11.2017.
 */

public class Message {
    private Date date;
    private String message;
    private Boolean isRead;
    private String sender;
    private String senderName;
    private String recipientName;
    private String recipient;

    public Message(String message, String sender, String recipient, String senderName, String recipientName) {
        this.date = new Date();
        this.message = message;
        this.isRead = false;
        this.sender = sender;
        this.senderName = senderName;
        this.recipient = recipient;
        this.recipientName = recipientName;
    }

    public Message() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
