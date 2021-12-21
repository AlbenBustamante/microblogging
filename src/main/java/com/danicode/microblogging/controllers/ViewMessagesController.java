package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewMessagesController {
    private int currentPage = 1, totalPages;
    private List<Message> messages;
    private JLabel lPageIndex;
    private final GUIViewMessages messagesTemplate;
    private final MessageService service;
    private final User userLogged;
    private final ViewPostTemplate[] postTemplate = new ViewPostTemplate[10];

    public ViewMessagesController(JFrame owner) {
        this.messagesTemplate = new GUIViewMessages(owner);
        this.service = new MessageService();
        this.userLogged = new UserService().getUserLogged();
        this.messages = this.service.getMessages(BlogConstants.LIST_MESSAGES, null);
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
            this.postTemplate[i].addMouseListener(this.popupAction(i));
            this.setActions(i);
            this.messagesTemplate.getCenterPane().add(this.postTemplate[i]);
            this.setData(i, this.messages.get(i));
        }
    }

    private void checkCurrentPage() {
        if (this.currentPage > this.totalPages) {
            this.currentPage = 1;
        }
    }

    private void refreshPage() {
        this.setTotalPages();
        this.checkCurrentPage();

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
            this.refreshPage();
        }
    }

    private void previousPage() {
        if (this.currentPage > 1) {
            this.currentPage --;
            this.refreshPage();
        }
    }

    private void checkSearchResult() {
        var optionSelected = String.valueOf(this.messagesTemplate.getCbSearchType().getSelectedItem());
        if (optionSelected.equals(BlogConstants.SEARCH_BY_MESSAGE)) {
            this.search(BlogConstants.LIST_BY_MESSAGE);
        }
        else if (optionSelected.equals(BlogConstants.SEARCH_MESSAGE_BY_USER)) {
            this.search(BlogConstants.LIST_USER_MESSAGES);
        }
        else {
            this.search(BlogConstants.LIST_MESSAGES);
        }
    }

    private void search(int filter) {
        var text = this.messagesTemplate.getTfSearch().getText().strip();
        var message = new Message();

        if (filter == BlogConstants.LIST_USER_MESSAGES) {
            message.setUser(new User("", "", "", text, ""));
        }
        else if (filter == BlogConstants.LIST_BY_MESSAGE) {
            message.setMessage(text);
        }

        this.messages = service.getMessages(filter, message);

        if (!this.messages.isEmpty()) {
            this.refreshPage();
        }
        else {
            JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
        }
    }

    private MouseAdapter popupAction(int index) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                postTemplate[index].getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
        };
    }

    private void viewProfile(int index) {
        var username = this.postTemplate[index].getLUsername().getText().split("@");
        var profile = this.service.getAuthor(username[1]);
        new ViewProfileController(profile);
    }

    private boolean isUserLoggedMessage(int index) {
        var username = this.postTemplate[index].getLUsername().getText().split("@")[1];
        return this.userLogged.getUsername().equals(username);
    }

    private void editMessage(int index) {
        if (this.isUserLoggedMessage(index)) {
            var dateTime = this.postTemplate[index].getLDateTime().getText();
            var messageToEdit = this.service.getMessage(dateTime);
            new PostEditMessageController(this.messagesTemplate, messageToEdit);
        }
        else {
            JOptionPane.showMessageDialog(null, "Sólo puedes editar tus propios mensajes",
                    this.userLogged.getUsername(), JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteMessage(int index) {
        if (this.isUserLoggedMessage(index)) {
            var dateTime = this.postTemplate[index].getLDateTime().getText();
            var idMessage = this.service.getMessage(dateTime).getIdMessage();

            var option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el mensaje?",
                    "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                var confirm = this.service.deleteMessage(idMessage) ?
                        "¡Mensaje eliminado correctamente!" : "Algo ocurrió mal";
                JOptionPane.showMessageDialog(null, confirm);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Sólo puedes eliminar tus propios mensajes",
                    this.userLogged.getUsername(), JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setActions(int index) {
        this.postTemplate[index].getMenuViewProfile().addActionListener(e -> this.viewProfile(index));
        this.postTemplate[index].getMenuEditMessage().addActionListener(e -> this.editMessage(index));
        this.postTemplate[index].getMenuDeleteMessages().addActionListener(e -> this.deleteMessage(index));
    }

    private void setActions() {
        this.messagesTemplate.getBExit().addActionListener(e -> this.messagesTemplate.dispose());
        this.messagesTemplate.getBNext().addActionListener(e -> this.nextPage());
        this.messagesTemplate.getBPrevious().addActionListener(e -> this.previousPage());
        this.messagesTemplate.getBSearch().addActionListener(e -> this.checkSearchResult());
        this.messagesTemplate.getTfSearch().addActionListener(e -> this.checkSearchResult());
    }

    private void init() {
        this.setTotalPages();
        this.setActions();
        this.loadMainData();
        this.createLabel();
    }
}
