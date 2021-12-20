package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.users.GUIEditProfile;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EditProfileController {
    private final GUIEditProfile template;
    private final UserService service;
    private final User user;
    private String name, lastName, password;

    public EditProfileController(JFrame owner, User user) {
        this.template = new GUIEditProfile(owner);
        this.service = new UserService();
        this.user = user;
        this.init();
    }

    private void setTexts() {
        this.template.getLUsername().setText("@" + this.user.getUsername() + " #" + this.user.getIdUser());
        this.template.getTfName().setText(this.user.getName());
        this.template.getTfLastName().setText(this.user.getLastName());
    }

    private void setData() {
        this.name = this.template.getTfName().getText().strip();
        this.lastName = this.template.getTfLastName().getText().strip();
        this.password = new String(this.template.getPfPassword().getPassword());
    }

    private void apply() {
        this.setData();
        var isBlank = this.name.equals("") || this.lastName.equals("") || this.password.equals("");

        if (!isBlank) {
            var newUser = new User(this.name, this.lastName, null, this.user.getUsername(), this.password);
            var message = this.service.updateUser(newUser) ? "Usuario actualizado correctamente" : "Algo ha ocurrido mal";
            JOptionPane.showMessageDialog(null, message);
        }
        else {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
        }
    }

    private void setActions() {
        this.setActions(e ->
                this.apply(), this.template.getTfName(), this.template.getTfLastName(), this.template.getPfPassword());
        this.template.getBApply().addActionListener(e -> this.apply());
    }

    private void setActions(ActionListener event, JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.addActionListener(event));
    }

    private void init() {
        this.setActions();
        this.setTexts();
    }
}
