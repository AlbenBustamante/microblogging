package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.login.GUILogIn;
import com.danicode.microblogging.gui.login.LogInTemplate;
import com.danicode.microblogging.gui.login.SignUpTemplate;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;

public class LogInController {
    private GUILogIn logIn;
    private LogInTemplate logInTemplate;
    private SignUpTemplate signUpTemplate;
    private UserService userService;

    public LogInController() {
        this.logIn = new GUILogIn();
        this.logInTemplate = this.logIn.getLogInTemplate();
        this.signUpTemplate = this.logIn.getSignUpTemplate();
        this.userService = new UserService();
        this.setActions();
    }

    private void logIn() {
        JOptionPane.showMessageDialog(null, "INICIANDO SESIÓN...");
    }

    private void signUp() {
        JOptionPane.showMessageDialog(null, "REGISTRANDO...");
    }

    private void setActions() {
        this.logInTemplate.getbLogIn().addActionListener(e -> this.logIn());
        this.logInTemplate.getJUser().addActionListener(e -> this.logIn());
        this.logInTemplate.getJPassword().addActionListener(e -> this.logIn());
        this.signUpTemplate.getBSignUp().addActionListener(e -> this.signUp());
        this.signUpTemplate.getJName().addActionListener(e -> this.signUp());
        this.signUpTemplate.getJLastName().addActionListener(e -> this.signUp());
        this.signUpTemplate.getJEmail().addActionListener(e -> this.signUp());
        this.signUpTemplate.getJUsername().addActionListener(e -> this.signUp());
        this.signUpTemplate.getJPassword().addActionListener(e -> this.signUp());
    }
}
