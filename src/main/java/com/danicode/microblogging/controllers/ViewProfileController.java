package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.users.GUIViewProfile;
import com.danicode.microblogging.model.domain.User;

public class ViewProfileController {
    private final GUIViewProfile template;
    private final User user;

    public ViewProfileController(User user) {
        this.user = user;
        this.template = new GUIViewProfile(null);
        this.template.getBExit().addActionListener(l -> this.template.dispose());
        this.setLabelTexts();
    }

    private void setLabelTexts() {
        this.template.getLUserId().setText("Identificador: #" + this.user.getIdUser());
        this.template.getLFullName().setText("Nombre Completo: " + this.user.getName() + " " + this.user.getLastName());
        this.template.getLEmail().setText("Email: " + this.user.getEmail());
        this.template.getLUsername().setText("Nombre de Usuario: @" + this.user.getUsername());
    }
}
