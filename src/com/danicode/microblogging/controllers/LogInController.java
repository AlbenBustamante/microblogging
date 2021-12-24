package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.login.GUILogIn;
import com.danicode.microblogging.gui.login.LogInTemplate;
import com.danicode.microblogging.gui.login.SignUpTemplate;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Esta clase se encarga de gestionar a los usuarios, es decir, establecer un usuario logueado y/o registrar un nuevo usuario.
 * */
public class LogInController {
    private final GUILogIn logIn;
    private final LogInTemplate logInTemplate;
    private final SignUpTemplate signUpTemplate;
    private final UserService userService;
    private User user;
    private String logUsername, logPassword, signName, signLastName, signEmail, signUsername, signPassword;

    /**
     * No recibe parámetros, simplemente se encarga de crear las instancias necesarias.
     * @see com.danicode.microblogging.gui.login.GUILogIn
     * @see com.danicode.microblogging.gui.login.LogInTemplate
     * @see com.danicode.microblogging.gui.login.SignUpTemplate
     * @see com.danicode.microblogging.services.UserService
     * */
    public LogInController() {
        this.logIn = new GUILogIn();
        this.logInTemplate = this.logIn.getLogInTemplate();
        this.signUpTemplate = this.logIn.getSignUpTemplate();
        this.userService = new UserService();
        this.user = new User();
        this.setActions();
    }

    /**
     * Obtiene los datos de los campos de texto para iniciar sesión y los guarda en su respectiva variable.
     * */
    private void setLogInData() {
        this.logUsername = this.logInTemplate.getJUser().getText().strip();
        this.logPassword = new String(this.logInTemplate.getJPassword().getPassword()).strip();
    }

    /**
     * Establece los datos mediante {@code setLogInData}
     * @return Devuelve verdadero si todos o algún dato está vacío.
     * */
    private boolean isLogInEmpty() {
        this.setLogInData();
        return this.logUsername.equals("") || this.logPassword.equals("");
    }

    /**
     * Hace una consulta a la base de datos y establece al usuario logueado.
     * @return Devuelve verdadero si no ocurrió ningún error.
     * */
    private boolean setUserLogged() {
        this.user = new User("", "", this.logUsername, this.logUsername, this.logPassword);
        return this.userService.setUserLogged(this.user);
    }

    /**
     * Salimos de la ventana para iniciar sesión y vamos al menú principal del programa.
     * */
    private void run() {
        var username = this.userService.getUserLogged().getUsername();
        JOptionPane.showMessageDialog(null, "¡Bienvenido " + username + "!",
                "Sesión abierta", JOptionPane.INFORMATION_MESSAGE);
        new MainMenuController();
        this.logIn.dispose();
    }

    /**
     * Realiza varias validaciones y va al menú principal si los datos están bien introducidos.
     * */
    private void logIn() {
        if (!this.isLogInEmpty()) {
            if (this.setUserLogged()) {
                this.run();
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

    /**
     * Obtiene los datos de los campos de texto para registrar un nuevo usuario y los guarda en su respectiva variable.
     * */
    private void setSignUpData() {
        this.signName = this.signUpTemplate.getJName().getText().strip();
        this.signLastName = this.signUpTemplate.getJLastName().getText().strip();
        this.signEmail = this.signUpTemplate.getJEmail().getText().strip();
        this.signUsername = this.signUpTemplate.getJUsername().getText().strip();
        this.signPassword = new String(this.signUpTemplate.getJPassword().getPassword()).strip();
    }

    /**
     * Establece los datos mediante {@code setSignUpData}
     * @return Devuelve verdadero si todos o algún campo está vacío.
     * */
    private boolean isSignUpEmpty() {
        this.setSignUpData();

        return this.signName.equals("") || this.signLastName.equals("") || this.signEmail.equals("") ||
                this.signUsername.equals("") || this.signPassword.equals("");
    }

    /**
     * Consulta a la base de datos si pudo registrar un nuevo usuario
     * @return Devuelve verdadero si no hubo ningún error
     * */
    private boolean newUserRegistered() {
        this.user = new User(this.signName, this.signLastName, this.signEmail, this.signUsername, this.signPassword);
        return this.userService.registerNewUser(this.user);
    }

    /**
     * Simplemente muestra un mensaje dependiendo si se logró registrar el usuario.
     * */
    private void signUp() {
        if (!this.isSignUpEmpty()) {
            var message = this.newUserRegistered() ? "¡Usuario registrado con éxito!" :
                    "El nombre de usuario y/o correo electrónico ya se encuentra registrado";
            var title = this.newUserRegistered() ? "Nuevo usuario: " + this.user.getUsername() :
                    "No fue posible registrarte";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos para registrarte",
                    "Datos en blanco", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Establece las acciones a los campos de texto y botones.
     * */
    private void setActions() {
        this.setActions(e -> this.logIn(), this.logInTemplate.getbLogIn(), this.logInTemplate.getJUser(), this.logInTemplate.getJPassword());
        this.setActions(e -> this.signUp(), this.signUpTemplate.getBSignUp(), this.signUpTemplate.getJName(), this.signUpTemplate.getJLastName(),
                this.signUpTemplate.getJEmail(), this.signUpTemplate.getJUsername(), this.signUpTemplate.getJPassword());
    }

    /**
     * Establece la misma acción a varios campos de texto y/o contraseña.
     * */
    private void setActions(ActionListener event, JButton button, JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.addActionListener(event));
        button.addActionListener(event);
    }
}
