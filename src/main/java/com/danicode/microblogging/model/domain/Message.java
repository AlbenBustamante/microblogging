package com.danicode.microblogging.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final static String DATE_TIME_FORMAT = "EEE, dd-MM-yyyy hh:mm:ss a";
    private int idMessage;
    private User user;
    private String dateTime, message;

    public Message() { }

    public Message(User user, String message) {
        this.user = user;
        this.message = message;
        this.setDateTime();
    }

    public Message(int idMessage, String message) {
        this.idMessage = idMessage;
        this.message = message;
    }

    public Message(int idMessage, User user, String dateTime, String message) {
        this.idMessage = idMessage;
        this.user = user;
        this.dateTime = dateTime;
        this.message = message;
    }

    public void setIdMessage(int idMessage) { this.idMessage = idMessage; }

    public void setUser(User user) { this.user = user; }

    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public void setDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        this.dateTime = dateTime.format(formatter);
    }

    public void setMessage(String message) { this.message = message; }

    public int getIdMessage() { return this.idMessage; }

    public User getUser() { return this.user; }

    public String getDateTime() { return this.dateTime; }

    public String getMessage() { return this.message; }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + this.idMessage +
                ", user=" + this.user +
                ", dateTime='" + this.dateTime + '\'' +
                ", message='" + this.message + '\'' +
                '}';
    }
}
