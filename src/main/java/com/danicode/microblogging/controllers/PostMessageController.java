package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIPostMessage;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PostMessageController implements DocumentListener {
    private GUIPostMessage postMessage;
    private UserService userService;
    private MessageService messageService;

    public PostMessageController() {
        this.postMessage = new GUIPostMessage(null);
        this.init();
    }

    private void postMessage() {
        var messageText = this.postMessage.getJPost().getText().strip();
        if (messageText.length() <= 140) {
            var message = new Message(this.userService.getUserLogged(), messageText);
            this.messageService.createMessage(message);
            JOptionPane.showMessageDialog(null, "¡Mensaje publicado con éxito!");
            var option = JOptionPane.showConfirmDialog(null, "¿Deseas publicar otro?",
                    "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option != 0) {
                this.postMessage.dispose();
            }
        }
    }

    private void setActions() {
        var document = this.postMessage.getJPost().getDocument();
        document.addDocumentListener(this);

        this.postMessage.getBExit().addActionListener(e -> this.postMessage.dispose());
        this.postMessage.getBPost().addActionListener(e -> this.postMessage());
    }

    private void init() {
        this.setActions();
    }

    private void checkCharacters() {
        var length = this.postMessage.getJPost().getText().strip().length();
        var remaining = 0;

        if (length > 140) {
            remaining = - (this.postMessage.POST_LENGTH - length);
            this.postMessage.getLCharacters().setText("Límite excedido: " + remaining);
            this.postMessage.getLCharacters().setForeground(Color.RED);
        }
        else {
            remaining = this.postMessage.POST_LENGTH - length;
            this.postMessage.getLCharacters().setText(this.postMessage.CHARACTERS_REMAINING + remaining);
            this.postMessage.getLCharacters().setForeground(Color.DARK_GRAY);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        this.checkCharacters();
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        this.checkCharacters();
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
    }
}
