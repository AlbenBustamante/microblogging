package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.login.GUILogIn;
import com.danicode.microblogging.gui.login.LogInTemplate;
import com.danicode.microblogging.gui.login.SignUpTemplate;
import com.danicode.microblogging.services.UserService;

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
    }
}
