package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIPostMessage;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PostMessageController implements DocumentListener {
    private GUIPostMessage postMessage;
    private MessageService messageService;
    private UserService userService;

    public PostMessageController() {
        this.postMessage = new GUIPostMessage(null);
        this.messageService = new MessageService();
        this.userService = new UserService();
        this.init();
    }

    private void postMessage() {
        var messageText = this.postMessage.getJPost().getText().strip();
        if (messageText.length() >= 1 && messageText.length() <= 140) {
            var message = new Message(this.userService.getUserLogged(), messageText);
            if (this.messageService.createMessage(message)) {
                this.postMessage.getJPost().setText("");
                JOptionPane.showMessageDialog(null, "¡Mensaje publicado con éxito!");
                var option = JOptionPane.showConfirmDialog(null, "¿Deseas publicar otro?",
                        "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option != 0) {
                    this.postMessage.dispose();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Algo salió mal");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "El mensaje debe tener entre 1 y 140 caracteres");
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
