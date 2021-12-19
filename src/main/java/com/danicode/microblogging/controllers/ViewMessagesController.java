package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;

import javax.swing.*;
import java.util.List;

public class ViewMessagesController {
    private int currentPage = 1, totalPages;
    private List<Message> messages;
    private JLabel lPageIndex;
    private final GUIViewMessages messagesTemplate;
    private final MessageService service;
    private final ViewPostTemplate[] postTemplate = new ViewPostTemplate[10];

    public ViewMessagesController() {
        this.messagesTemplate = new GUIViewMessages(null);
        this.service = new MessageService();
        this.messages = this.service.getMessages();
        this.init();
    }

    private void setTotalPages() {
        this.totalPages = 0;
        for (int i = 0; i < this.messages.size(); i ++) {
            if (i % 10 == 0) {
                this.totalPages ++;
            }
        }
    }

    private void resetLabel() {
        this.lPageIndex.setText("Página " + this.currentPage + " de " + this.totalPages);
    }

    private void createLabel() {
        this.lPageIndex = new JLabel("Página " + this.currentPage + " de " + this.totalPages);
        this.messagesTemplate.getCenterPane().add(this.lPageIndex);
    }

    private void setData(int index, Message message) {
        this.postTemplate[index].getLFullName().setText(message.getUser().getName() + " " + message.getUser().getLastName());
        this.postTemplate[index].getLUsername().setText("@" + message.getUser().getUsername());
        this.postTemplate[index].getLDateTime().setText(message.getDateTime());
        this.postTemplate[index].getTaMessage().setText(message.getMessage());
    }

    private void loadMainData() {
        var size = this.messages.size();
        var cycles = Math.min(size, 10);

        for (int i = 0; i < cycles; i ++) {
            this.postTemplate[i] = new ViewPostTemplate();
            this.setData(i, this.messages.get(i));
            this.messagesTemplate.getCenterPane().add(this.postTemplate[i]);
        }
    }

    private void changePage() {
        var elements = (this.currentPage - 1) * 10;

        for (int i = elements, j = 0; j < 10; i ++, j ++) {
            try {
                this.setData(j, this.messages.get(i));
                this.postTemplate[j].setVisible(true);
            } catch (IndexOutOfBoundsException ex) {
                this.postTemplate[j].setVisible(false);
            }
        }

        this.resetLabel();
    }

    private void nextPage() {
        if (this.currentPage < this.totalPages) {
            this.currentPage ++;
            this.changePage();
        }
    }

    private void previousPage() {
        if (this.currentPage > 1) {
            this.currentPage --;
            this.changePage();
        }
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
        this.setTotalPages();
        this.setActions();
        this.loadMainData();
        this.createLabel();
    }
}
