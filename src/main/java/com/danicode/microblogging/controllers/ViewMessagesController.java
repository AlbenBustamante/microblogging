package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;

public class ViewMessagesController {
    private ViewPostTemplate postTemplate;
    private final GUIViewMessages messagesTemplate;
    private final MessageService service;

    public ViewMessagesController() {
        this.postTemplate = new ViewPostTemplate();
        this.messagesTemplate = new GUIViewMessages(null);
        this.service = new MessageService();
        this.init();
    }

    private void setData(Message message) {
        this.postTemplate = new ViewPostTemplate();
        this.postTemplate.getLFullName().setText(message.getUser().getName() + " " + message.getUser().getLastName());
        this.postTemplate.getLUsername().setText("@" + message.getUser().getUsername());
        this.postTemplate.getLDateTime().setText(message.getDateTime());
        this.postTemplate.getTaMessage().setText(message.getMessage());
    }

    private void loadMainData() {
        var messages = this.service.getMessages();
        messages.forEach(message -> {
             this.setData(message);
             this.messagesTemplate.getCenterPane().add(this.postTemplate);
        });
    }

    private void nextPage() { }

    private void previousPage() { }

    private void search() {
        var optionSelected = String.valueOf(this.messagesTemplate.getCbSearchType().getSelectedItem());
        if (optionSelected.equals(BlogConstants.SEARCH_BY_MESSAGE)) {
            this.searchByMessage();
        }
        else if (optionSelected.equals(BlogConstants.SEARCH_MESSAGE_BY_USER)) {
            this.searchMessageByUser();
        }
        else {
            this.loadMainData();
        }
    }

    private void searchByMessage() { }

    private void searchMessageByUser() { }

    private void setActions() {
        this.messagesTemplate.getBExit().addActionListener(e -> this.messagesTemplate.dispose());
        this.messagesTemplate.getBNext().addActionListener(e -> this.nextPage());
        this.messagesTemplate.getBPrevious().addActionListener(e -> this.previousPage());
        this.messagesTemplate.getBSearch().addActionListener(e -> this.search());
        this.messagesTemplate.getTfSearch().addActionListener(e -> this.search());
    }

    private void init() {
        this.setActions();
        this.loadMainData();
    }
}
