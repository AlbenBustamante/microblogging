package com.danicode.microblogging.gui.users;

import com.danicode.microblogging.gui.model.GUIDialog;
import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class GUIEditProfile extends GUIDialog {
    private JLabel lName, lLastName, lEmail, lPassword, lUsername;
    private JTextField tfName, tfLastName, tfEmail;
    private JPasswordField pfPassword;
    private JButton bAccept, bCancel;
    private JPanel northPane, centerPane, southPane;
    private IGridBagLayout gbc;
    private static final int WIDTH = 3;

    public GUIEditProfile(JFrame owner) {
        super(owner, 420, 500, "Editar y/o actualizar información", false, false, new BorderLayout());
    }

    private void createLabels() {
        this.lName = new JLabel("Nombres");
        this.lLastName = new JLabel("Apellidos");
        this.lEmail = new JLabel("Email");
        this.lPassword = new JLabel("Constraseña");
        this.lUsername = new JLabel("Usuario");
        this.lUsername.setFont(this.lUsername.getFont().deriveFont(15f));
    }

    private void createFields() {
        this.tfName = new JTextField(12);
        this.tfLastName = new JTextField(12);
        this.tfEmail = new JTextField(12);
        this.pfPassword = new JPasswordField(12);
    }

    private void createButtons() {
        this.bAccept = new JButton("Aceptar");
        this.bCancel = new JButton("Volver");
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.centerPane = new JPanel();
        this.southPane = new JPanel();

        this.centerPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
    }

    private void setLayouts() {
        this.northPane.setLayout(new FlowLayout(FlowLayout.LEFT, 24, 20));
        this.centerPane.setLayout(new GridBagLayout());
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        this.gbc = () -> this.centerPane;
    }

    private void design() {
        this.northPane.add(this.lUsername);

        final int WIDTH = 3;
        this.gbc.GBC.ipadx = 8;
        this.gbc.GBC.ipady = 4;

        this.gbc.addSpaces(0, WIDTH);

        this.addConstraints(0, this.lName, this.tfName);
        this.addConstraints(3, this.lLastName, this.tfLastName);
        this.addConstraints(4, this.lEmail, this.tfEmail);
        this.addConstraints(6, this.lPassword, this.pfPassword);

        this.gbc.addVertical(1, 2, 1, 1.0, 1.0, GridBagConstraints.HORIZONTAL, this.lLastName,
                1.0, 0, GridBagConstraints.HORIZONTAL, this.tfLastName);
        this.gbc.addFinalSpaces(WIDTH, 2);

        this.southPane.add(this.bAccept);
        this.southPane.add(this.bCancel);
        this.southPane.add(new JLabel("    "));

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
        this.getContentPane().add(this.centerPane, BorderLayout.CENTER);
        this.getContentPane().add(this.southPane, BorderLayout.SOUTH);
    }

    private void addConstraints(int y, JLabel label, JTextField field) {
        this.gbc.addVertical(1, y, 1, 1.0, 0, GridBagConstraints.HORIZONTAL, label,
                1.0, 0, GridBagConstraints.HORIZONTAL, field);
        this.gbc.addFinalSpaces(WIDTH, y + 2);
    }

    @Override
    protected void close() {
        this.dispose();
    }

    @Override
    protected void init() {
        this.createPanels();
        this.createLabels();
        this.createFields();
        this.createButtons();
        this.setLayouts();
        this.design();
    }

    public JLabel getLUsername() { return this.lUsername; }

    public JTextField getTfName() { return this.tfName; }

    public JTextField getTfLastName() { return this.tfLastName; }

    public JTextField getTfEmail() { return this.tfEmail; }

    public JPasswordField getPfPassword() { return this.pfPassword; }

    public JButton getBAccept() { return this.bAccept; }

    public JButton getBCancel() { return this.bCancel; }
}
