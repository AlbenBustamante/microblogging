package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIPostMessage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        this.postMessage.getLCharacters().setText("HOLAAA");
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        this.postMessage.getLCharacters().setText("CHAAAAO");
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
    }
}
