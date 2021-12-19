package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;

import javax.swing.*;

public class ViewMessagesController {
    private ViewPostTemplate postTemplate;
    private GUIViewMessages messagesTemplate;
    private MessageService service;

    public ViewMessagesController() {
        this.postTemplate = new ViewPostTemplate();
        this.messagesTemplate = new GUIViewMessages(null);
        this.service = new MessageService();
        this.init();
    }

    private void setData(Message message) {
        this.postTemplate.getLFullName().setText(message.getUser().getName() + " " + message.getUser().getLastName());
        this.postTemplate.getLUsername().setText("@" + message.getUser().getUsername());
        this.postTemplate.getLDateTime().setText(message.getDateTime());
        this.postTemplate.getTaMessage().setText(message.getMessage());
    }

    private void loadMainData() {
        var messages = this.service.getMessages();
        messages.forEach(message -> {
             this.postTemplate = new ViewPostTemplate();
             this.setData(message);
             this.messagesTemplate.getCenterPane().add(this.postTemplate);
        });
    }

    private void init() {
        this.loadMainData();
    }
}
