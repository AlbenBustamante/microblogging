package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.users.GUIEditProfile;
import com.danicode.microblogging.model.domain.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EditProfileController {
    private final GUIEditProfile template;
    private final User user;

    public EditProfileController(JFrame owner, User user) {
        this.template = new GUIEditProfile(owner);
        this.user = user;
        this.init();
    }

    private void setTexts() {
        this.template.getLUsername().setText("@" + this.user.getUsername() + " #" + this.user.getIdUser());
        this.template.getTfName().setText(this.user.getName());
        this.template.getTfLastName().setText(this.user.getLastName());
        this.template.getTfEmail().setText(this.user.getEmail());
    }

    private void apply() {

    }

    private void setActions() {
        this.setActions(e -> this.apply(), this.template.getTfName(), this.template.getTfLastName(),
                this.template.getTfEmail(), this.template.getPfPassword());
    }

    private void setActions(ActionListener event, JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.addActionListener(event));
    }

    private void init() {
        this.setActions();
        this.setTexts();
    }
}
