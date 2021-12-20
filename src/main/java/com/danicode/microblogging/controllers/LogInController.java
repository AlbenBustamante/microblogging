package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.login.GUILogIn;
import com.danicode.microblogging.gui.login.LogInTemplate;
import com.danicode.microblogging.gui.login.SignUpTemplate;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LogInController {
    private GUILogIn logIn;
    private LogInTemplate logInTemplate;
    private SignUpTemplate signUpTemplate;
    private UserService userService;
    private User user;
    private String logUsername, logPassword, signName, signLastName, signEmail, signUsername, signPassword;

    public LogInController() {
        this.logIn = new GUILogIn();
        this.logInTemplate = this.logIn.getLogInTemplate();
        this.signUpTemplate = this.logIn.getSignUpTemplate();
        this.userService = new UserService();
        this.user = new User();
        this.setActions();
    }

    private void setLogInData() {
        this.logUsername = this.logInTemplate.getJUser().getText().strip();
        this.logPassword = new String(this.logInTemplate.getJPassword().getPassword()).strip();
    }

    private boolean isLogInEmpty() {
        this.setLogInData();

        return this.logUsername.equals("") || this.logPassword.equals("");
    }

    private boolean setUserLogged() {
        this.user = new User("", "", this.logUsername, this.logUsername, this.logPassword);

        return this.userService.setUserLogged(this.user);
    }

    private void logIn() {
        if (!this.isLogInEmpty()) {
            if (this.setUserLogged()) {
                JOptionPane.showMessageDialog(null,
                        "¡Bienvenido " + this.userService.getUserLogged().getUsername() + "!",
                        "Sesión abierta", JOptionPane.INFORMATION_MESSAGE);
                new MainMenuController();
                this.logIn.dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Verifica los datos nuevamente",
                        "Datos no coinciden", JOptionPane.WARNING_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Por favor, llena los datos para iniciar sesión",
                    "Datos en blanco", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setSignUpData() {
        this.signName = this.signUpTemplate.getJName().getText().strip();
        this.signLastName = this.signUpTemplate.getJLastName().getText().strip();
        this.signEmail = this.signUpTemplate.getJEmail().getText().strip();
        this.signUsername = this.signUpTemplate.getJUsername().getText().strip();
        this.signPassword = new String(this.signUpTemplate.getJPassword().getPassword()).strip();
    }

    private boolean isSignUpEmpty() {
        this.setSignUpData();

        return this.signName.equals("") || this.signLastName.equals("") || this.signEmail.equals("") ||
                this.signUsername.equals("") || this.signPassword.equals("");
    }

    private boolean newUserRegistered() {
        this.user = new User(this.signName, this.signLastName, this.signEmail, this.signUsername, this.signPassword);

        return this.userService.registerNewUser(this.user);
    }

    private void signUp() {
        if (!this.isSignUpEmpty()) {
            if (this.newUserRegistered()) {
                JOptionPane.showMessageDialog(null, "¡Usuario registrado con éxito!",
                        "Nuevo usuario: " + this.user.getUsername(), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "El nombre de usuario y/o correo electrónico ya se encuentra registrado.",
                        "No fue posible registrar un nuevo usuario", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos para registrarte",
                    "Datos en blanco", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setActions() {
        this.setActions(e -> this.logIn(), this.logInTemplate.getbLogIn(), this.logInTemplate.getJUser(), this.logInTemplate.getJPassword());
        this.setActions(e -> this.signUp(), this.signUpTemplate.getBSignUp(), this.signUpTemplate.getJName(), this.signUpTemplate.getJLastName(),
                this.signUpTemplate.getJEmail(), this.signUpTemplate.getJUsername(), this.signUpTemplate.getJPassword());
    }

    private void setActions(ActionListener event, JButton button, JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.addActionListener(event));
        button.addActionListener(event);
    }
}
