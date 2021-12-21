package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.users.GUIViewProfile;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.MessageService;

import java.awt.*;
import java.util.List;

public class ViewProfileController {
    private final GUIViewProfile template;
    private final User user;
    private final List<Message> userMessages;

    public ViewProfileController(Window owner, User user) {
        this.user = user;
        var message = new Message(user, "");
        this.userMessages = new MessageService().getMessages(BlogConstants.LIST_USER_MESSAGES, message);
        this.template = new GUIViewProfile(owner);
        this.template.getBExit().addActionListener(l -> this.template.dispose());
        this.setLabelTexts();
    }

    private void setLabelTexts() {
        this.template.getLUserId().setText("Identificador: #" + this.user.getIdUser());
        this.template.getLFullName().setText("Nombre Completo: " + this.user.getName() + " " + this.user.getLastName());
        this.template.getLEmail().setText("Email: " + this.user.getEmail());
        this.template.getLUsername().setText("Nombre de Usuario: @" + this.user.getUsername());
        this.template.getlTotalMessages().setText("Mensajes publicados: " + this.userMessages.size());
    }
}
