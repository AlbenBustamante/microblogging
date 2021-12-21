package com.danicode.microblogging.gui.users;

import com.danicode.microblogging.gui.model.GUIDialog;
import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class GUIViewProfile extends GUIDialog {
    private JLabel lUserId, lFullName, lEmail, lUsername, lTotalMessages;
    private JButton bExit;
    private JPanel centerPane, southPane;
    private IGridBagLayout gbc;

    public GUIViewProfile(JFrame owner) {
        super(owner, 474, 304, "Perfil de Usuario", false, false, new BorderLayout());
    }

    private void createLabels() {
        this.lUserId = new JLabel();
        this.lFullName = new JLabel();
        this.lEmail = new JLabel();
        this.lUsername = new JLabel();
        this.lTotalMessages = new JLabel();
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
        this.gbc = () -> this.centerPane;
    }

    private void design() {
        final int WIDTH = 3;

        this.gbc.addSpaces(0, WIDTH);

        this.gbc.addGBC(1, 1, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lUserId);
        this.gbc.addFinalSpaces(WIDTH, 1);

        this.gbc.addGBC(1, 3, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lFullName);
        this.gbc.addFinalSpaces(WIDTH, 3);

        this.gbc.addGBC(1, 5, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lEmail);
        this.gbc.addFinalSpaces(WIDTH, 5);

        this.gbc.addGBC(1, 7, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lUsername);
        this.gbc.addFinalSpaces(WIDTH, 7);

        this.gbc.addGBC(1, 9, 1, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, this.lTotalMessages);
        this.gbc.addFinalSpaces(WIDTH, 9);

        this.southPane.add(this.bExit);

        this.getContentPane().add(this.gbc.pane(), BorderLayout.CENTER);
        this.getContentPane().add(this.southPane, BorderLayout.SOUTH);
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
        this.design();
    }

    public JLabel getLUserId() { return this.lUserId; }

    public JLabel getLFullName() { return this.lFullName; }

    public JLabel getLEmail() { return this.lEmail; }

    public JLabel getLUsername() { return this.lUsername; }

    public JLabel getlTotalMessages() { return this.lTotalMessages; }

    public JButton getBExit() { return this.bExit; }
}
