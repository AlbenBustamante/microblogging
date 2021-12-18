package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIPostMessage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PostMessageController implements DocumentListener {
    private GUIPostMessage postMessage;

    public PostMessageController() {
        this.postMessage = new GUIPostMessage(null);
        this.init();
    }

    private void setActions() {
        var document = this.postMessage.getJPost().getDocument();
        document.addDocumentListener(this);
    }

    private void init() {
        this.setActions();
    }

    private void checkCharacters() {
        var length = this.postMessage.getJPost().getText().strip().length();
        var remaining = 0;
        var color = Color.BLACK;
        var text = "";

        if (length > 140) {
            remaining = - (this.postMessage.POST_LENGTH - length) + 1;
            text = "Límite excedido: " + --remaining;
            color = Color.RED;
        }
        else {
            remaining = this.postMessage.POST_LENGTH - length;
            text = this.postMessage.CHARACTERS_REMAINING + remaining;
        }

        this.postMessage.getLCharacters().setText(text);
        this.postMessage.getLCharacters().setForeground(color);
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
