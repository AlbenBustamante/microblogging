package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;

import javax.swing.*;
import java.util.List;

public class ViewMessagesController {
    private int index = 1, pages;
    private ViewPostTemplate postTemplate;
    private List<Message> messages;
    private JLabel lPageIndex;
    private final GUIViewMessages messagesTemplate;
    private final MessageService service;

    public ViewMessagesController() {
        this.postTemplate = new ViewPostTemplate();
        this.messagesTemplate = new GUIViewMessages(null);
        this.service = new MessageService();
        this.messages = this.service.getMessages();
        this.init();
    }

    private void setNumberPages() {
        this.pages = 0;
        for (int i = 0; i < this.messages.size(); i ++) {
            if (i % 10 == 0) {
                this.pages ++;
            }
        }
    }

    private void createLabel() {
        this.lPageIndex = new JLabel("Página " + this.index + " de " + this.pages);
        this.messagesTemplate.getCenterPane().add(this.lPageIndex);
    }

    private void setData(Message message) {
        this.postTemplate = new ViewPostTemplate();
        this.postTemplate.getLFullName().setText(message.getUser().getName() + " " + message.getUser().getLastName());
        this.postTemplate.getLUsername().setText("@" + message.getUser().getUsername());
        this.postTemplate.getLDateTime().setText(message.getDateTime());
        this.postTemplate.getTaMessage().setText(message.getMessage());
    }

    private void loadMainData() {
        var size = this.messages.size();

        if (size <= 10) {
            this.messages.forEach(message -> {
                this.setData(message);
                this.messagesTemplate.getCenterPane().add(this.postTemplate);
            });
        }
        else {
            for (int i = 0; i < 10; i ++) {
                var message = this.messages.get(i);
                this.setData(message);
                this.messagesTemplate.getCenterPane().add(this.postTemplate);
            }
        }
    }

    private void nextPage() {
        this.index ++;
    }

    private void previousPage() {
        this.index --;
    }

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
        this.setNumberPages();
        this.setActions();
        this.loadMainData();
        this.createLabel();
    }
}
