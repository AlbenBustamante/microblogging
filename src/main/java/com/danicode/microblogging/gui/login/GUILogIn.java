package com.danicode.microblogging.gui.login;

import com.danicode.microblogging.gui.model.GUIWindow;

import javax.swing.*;
import java.awt.*;

public class GUILogIn extends GUIWindow {
    private JTabbedPane tabbed;
    private LogInTemplate logInTemplate;
    private SignUpTemplate signUpTemplate;

    public GUILogIn() {
        super(500, 480, "Microblogging", false, new BorderLayout());
    }

    private void createTemplates() {
        this.logInTemplate = new LogInTemplate();
        this.signUpTemplate = new SignUpTemplate();
    }

    private void createTabbed() {
        this.tabbed = new JTabbedPane();
        this.tabbed.add("Iniciar Sesión", this.logInTemplate);
        this.tabbed.add("Registrarse", this.signUpTemplate);
    }

    @Override
    protected void close() {
        System.exit(0);
    }

    @Override
    protected void init() {
        this.createTemplates();
        this.createTabbed();
        this.getContentPane().add(this.tabbed, BorderLayout.CENTER);
    }

    public LogInTemplate getLogInTemplate() { return this.logInTemplate; }

    public SignUpTemplate getSignUpTemplate() { return this.signUpTemplate; }
}
