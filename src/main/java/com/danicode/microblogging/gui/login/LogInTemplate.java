package com.danicode.microblogging.gui.login;

import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class LogInTemplate extends JPanel {
    private IGridBagLayout gbc;
    private JLabel lUser, lPassword;
    private JTextField jUser;
    private JPasswordField jPassword;
    private JButton bLogIn;

    public LogInTemplate() {
        this.init();
    }

    private void createLabels() {
        this.lUser = new JLabel("Nombre de Usuario o Correo Electrónico");
        this.lPassword = new JLabel("Contraseña");
    }

    private void createFields() {
        this.jUser = new JTextField(24);
        this.jUser.setHorizontalAlignment(SwingConstants.CENTER);
        this.jPassword = new JPasswordField(24);
        this.jPassword.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void createButtons() {
        this.bLogIn = new JButton("Iniciar Sesión");
    }


    public JTextField getJUser() { return this.jUser; }

    public JPasswordField getJPassword() { return this.jPassword; }

    public JButton getbLogIn() { return this.bLogIn; }


    private void initLayout() {
        this.gbc = () -> this;
        this.setLayout(new GridBagLayout());
    }

    private void design() {
        final int WIDTH = 3;
        this.gbc.GBC.insets = new Insets(1, 7, 1, 7);
        this.gbc.GBC.ipadx = 10;
        this.gbc.GBC.ipady = 5;

        this.gbc.addSpaces(0, WIDTH);

        this.gbc.addVertical(1, 0, 1,
                0, 0, GridBagConstraints.NONE, this.lUser,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jUser);
        this.gbc.addFinalSpaces(WIDTH, 2);

        this.gbc.addVertical(1, 3, 1,
                0, 0, GridBagConstraints.NONE, this.lPassword,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jPassword);
        this.gbc.addFinalSpaces(WIDTH, 5);

        this.gbc.addGBC(1, 7, 1, 1, 0, 0, GridBagConstraints.NONE, this.bLogIn);
        this.gbc.addFinalSpaces(WIDTH, 7);
    }

    private void init() {
        this.createLabels();
        this.createFields();
        this.createButtons();
        this.initLayout();
        this.design();
    }
}
