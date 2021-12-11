package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.login.GUILogIn;
import com.danicode.microblogging.gui.login.LogInTemplate;
import com.danicode.microblogging.gui.login.SignUpTemplate;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;

public class LogInController {
    private GUILogIn logIn;
    private LogInTemplate logInTemplate;
    private SignUpTemplate signUpTemplate;
    private UserService userService;
    private User user;

    public LogInController() {
        this.logIn = new GUILogIn();
        this.logInTemplate = this.logIn.getLogInTemplate();
        this.signUpTemplate = this.logIn.getSignUpTemplate();
        this.userService = new UserService();
        this.user = new User();
        this.setActions();
    }

    private boolean isLogInEmpty() {
        var username = this.logInTemplate.getJUser().getText().trim();
        var password = new String(this.logInTemplate.getJPassword().getPassword());
        return username.equals("") || password.equals("");
    }

    private boolean setUserLogged() {
        var username = this.logInTemplate.getJUser().getText().trim();
        var password = new String(this.logInTemplate.getJPassword().getPassword());
        this.user.setEmail(username);
        this.user.setUsername(username);
        this.user.setPassword(password);

        return this.userService.setUserLogged(this.user);
    }

    private void logIn() {
        if (!this.isLogInEmpty()) {
            if (this.setUserLogged()) {
                JOptionPane.showMessageDialog(null,
                        "¡Bienvenido " + this.userService.getUserLogged().getUsername() + "!",
                        "Sesión abierta", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Verifica los datos nuevamente",
                        "Datos no coinciden", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Por favor, llena los datos para iniciar sesión",
                    "Datos en blanco", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isSignUpEmpty() {
        var name = this.signUpTemplate.getJName().getText().strip();
        var lastName = this.signUpTemplate.getJLastName().getText().strip();
        var email = this.signUpTemplate.getJEmail().getText().strip();
        var username = this.signUpTemplate.getJUsername().getText().strip();
        var password = new String(this.signUpTemplate.getJPassword().getPassword()).strip();

        return name.equals("") || lastName.equals("") || email.equals("") || username.equals("") || password.equals("");
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

class test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInController::new);
    }
}