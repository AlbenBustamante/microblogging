package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;

import javax.swing.*;

public class ViewPostController {
    private ViewPostTemplate postTemplate;
    private GUIViewMessages messagesTemplate;

    public ViewPostController() {
        this.postTemplate = new ViewPostTemplate();
        this.messagesTemplate = new GUIViewMessages(null);
        this.init();
    }

    private void init() {
        for (int i = 0; i < 10; i ++) {
            this.messagesTemplate.getCenterPane().add(new ViewPostTemplate());
        }

        this.messagesTemplate.getCenterPane().add(new JLabel("Página 1 de 10"));
    }
}
