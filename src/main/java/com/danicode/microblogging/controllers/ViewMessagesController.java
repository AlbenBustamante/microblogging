package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
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

    private void init() {
        var messages = this.service.getMessages();
        for (int i = 0; i < messages.size(); i ++) {
            this.messagesTemplate.getCenterPane().add(new ViewPostTemplate());
        }

        this.messagesTemplate.getCenterPane().add(new JLabel("Página 1 de 10"));
    }
}
