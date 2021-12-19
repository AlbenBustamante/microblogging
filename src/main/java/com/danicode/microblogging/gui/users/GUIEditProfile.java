package com.danicode.microblogging.gui.users;

import com.danicode.microblogging.gui.model.GUIDialog;
import com.danicode.microblogging.gui.model.layouts.IGridBagLayout;

import javax.swing.*;
import java.awt.*;

public class GUIEditProfile extends GUIDialog {
    private JLabel lName, lLastName, lEmail, lPassword, lUsername;
    private JTextField tfName, tfLastName, tfEmail;
    private JPasswordField pfPassword;
    private JButton bAccept, bCancel, bApply;
    private JPanel northPane, centerPane, southPane;
    private IGridBagLayout gbc;

    public GUIEditProfile(JFrame owner) {
        super(owner, 420, 500, "Editar y/o actualizar información", false, false, new GridBagLayout());
    }

    private void createLabels() {
        this.lName = new JLabel("Nombres");
        this.lLastName = new JLabel("Apellidos");
        this.lEmail = new JLabel("Email");
        this.lPassword = new JLabel("Constraseña");
        this.lUsername = new JLabel("Usuario");
        this.lUsername.setFont(this.lUsername.getFont().deriveFont(18f));
    }

    private void createFields() {
        this.tfName = new JTextField(12);
        this.tfLastName = new JTextField(12);
        this.tfEmail = new JTextField(12);
        this.pfPassword = new JPasswordField(12);
    }

    private void createButtons() {
        this.bAccept = new JButton("Aceptar");
        this.bCancel = new JButton("Cancelar");
        this.bApply = new JButton("Aplicar");
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.centerPane = new JPanel();
        this.southPane = new JPanel();
    }

    private void setLayouts() {
        this.northPane.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 20));
        this.centerPane.setLayout(new GridBagLayout());
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 18, 10));
        this.gbc = () -> this.centerPane;
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
    }
}
