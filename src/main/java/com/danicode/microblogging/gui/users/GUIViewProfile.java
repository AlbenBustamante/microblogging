package com.danicode.microblogging.gui.users;

import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIViewProfile extends GUIDialog {
    private String name, lastName, email, username;
    private int userId;
    private JLabel lUserId, lFullName, lEmail, lUsername;
    private JButton bExit;
    private JPanel centerPane, southPane;

    public GUIViewProfile(JFrame owner, int userId, String name, String lastName, String email, String username) {
        super(owner, 460, 500, "Perfil de Usuario", false, true, new BorderLayout());
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    private void createLabels() {
        this.lUserId = new JLabel("Identificador de Usuario: " + this.userId);
        this.lFullName = new JLabel("Nombre Completo: " + this.name + " " + this.lastName);
        this.lEmail = new JLabel("Email: " + this.email);
        this.lUsername = new JLabel("Nombre de Usuario: @" + this.username);
    }

    private void createButtons() {
        this.bExit = new JButton("Regresar");
    }

    private void createPanels() {
        this.centerPane = new JPanel();
        this.southPane = new JPanel();
        this.centerPane.setLayout(new GridBagLayout());
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        this.southPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    }

    @Override
    protected void close() {
        this.dispose();
    }

    @Override
    protected void init() {
        this.createPanels();
        this.createLabels();
        this.createButtons();
    }
}
