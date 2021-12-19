package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIViewMessages extends GUIDialog {
    private JPanel northPane;
    private JTextField tfSearch;
    private JComboBox<String> cbSearchType;
    private JButton bSearch;

    public GUIViewMessages(JFrame owner) {
        super(owner, 560, 700, "Microblogging", false, false, new BorderLayout());
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.northPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.northPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    private void createFields() {
        this.tfSearch = new JTextField("Buscador", 20);
    }

    private void createComboBoxes() {
        this.cbSearchType = new JComboBox<>(new String[] {
                "Mensajes", "Por Usuario"
        });
    }

    private void createButtons() {
        this.bSearch = new JButton("Buscar");
    }

    private void design() {
        /* North Pane */
        this.northPane.add(this.tfSearch);
        this.northPane.add(this.cbSearchType);
        this.northPane.add(this.bSearch);

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
    }

    @Override
    protected void close() {
        this.dispose();
    }

    @Override
    protected void init() {
        this.createPanels();
        this.createFields();
        this.createComboBoxes();
        this.createButtons();
        this.design();
    }

    public JTextField getTfSearch() { return this.tfSearch; }

    public JComboBox<String> getCbSearchType() { return this.cbSearchType; }

    public JButton getBSearch() { return this.bSearch; }

}
