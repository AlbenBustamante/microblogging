package com.danicode.microblogging.gui.login;

import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class SignUpTemplate extends JPanel {
    private IGridBagLayout gbc;
    private JLabel lName, lLastName, lEmail, lUsername, lPassword;
    private JTextField jName, jLastName, jEmail, jUsername;
    private JPasswordField jPassword;
    private JButton bSignUp;

    public SignUpTemplate() {
        this.init();
    }

    private void createLabels() {
        this.lName = new JLabel("Nombre");
        this.lLastName = new JLabel("Apellido");
        this.lEmail = new JLabel("Correo Electrónico");
        this.lUsername = new JLabel("Nombre de Usuario");
        this.lPassword = new JLabel("Contraseña");
    }

    private void createFields() {
        this.jName = this.createField(12);
        this.jLastName = this.createField(12);
        this.jEmail = this.createField(24);
        this.jUsername = this.createField(12);
        this.jPassword = new JPasswordField(12);
        this.jPassword.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void createButtons() {
        this.bSignUp = new JButton("Registrarse");
    }

    private JTextField createField(int columns) {
        JTextField jtf = new JTextField(columns);
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        return jtf;
    }


    public JTextField getJName() { return this.jName; }

    public JTextField getJLastName() { return this.jLastName; }

    public JTextField getJEmail() { return this.jEmail; }

    public JTextField getJUsername() { return this.jUsername; }

    public JPasswordField getJPassword() { return this.jPassword; }

    public JButton getBSignUp() { return this.bSignUp; }


    private void initLayout() {
        this.gbc = () -> this;
        this.setLayout(new GridBagLayout());
    }

    private void design() {
        final int WIDTH = 4;
        this.gbc.GBC.insets = new Insets(1, 7, 1, 7);
        this.gbc.GBC.ipadx = 10;
        this.gbc.GBC.ipady = 5;

        this.gbc.addSpaces(0, WIDTH);

        this.gbc.addVertical(1, 0, 1,
                0, 0, GridBagConstraints.NONE, this.lName,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jName);
        this.gbc.addVertical(2, 0, 1,
                0, 0, GridBagConstraints.NONE, this.lLastName,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jLastName);
        this.gbc.addFinalSpaces(WIDTH, 2);

        this.gbc.addVertical(1, 3, 2,
                0, 0, GridBagConstraints.NONE, this.lEmail,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jEmail);

        this.gbc.addVertical(1, 6, 1,
                0, 0, GridBagConstraints.NONE, this.lUsername,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jUsername);
        this.gbc.addVertical(2, 6, 1,
                0, 0, GridBagConstraints.NONE, this.lPassword,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.jPassword);
        this.gbc.addFinalSpaces(WIDTH, 8);

        this.gbc.addGBC(1, 10, 2, 1, 0, 0, GridBagConstraints.NONE, this.bSignUp);
        this.gbc.addFinalSpaces(WIDTH, 10);
    }


    private void init() {
        this.createButtons();
        this.createFields();
        this.createLabels();
        this.initLayout();
        this.design();
    }
}
