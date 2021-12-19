package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIViewMessages extends GUIDialog {
    private JPanel northPane, centerPane;
    private JTextField tfSearch;
    private JComboBox<String> cbSearchType;
    private JButton bSearch;
    private JScrollPane scCenterPane;

    public GUIViewMessages(JFrame owner) {
        super(owner, 560, 700, "Microblogging", false, false, new BorderLayout());
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.northPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.northPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        this.centerPane = new JPanel();
        this.centerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        this.centerPane.setPreferredSize(new Dimension(this.getContentPane().getWidth(), 4000));
        this.scCenterPane = new JScrollPane(this.centerPane);
        this.scCenterPane.setBorder(null);
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

        JTextField[] jtf = new JTextField[100];
        /* Center Pane */
        for (int i = 0; i < jtf.length; i ++) {
            jtf[i] = new JTextField(30);
            this.centerPane.add(jtf[i]);
        }

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
        this.getContentPane().add(this.scCenterPane, BorderLayout.CENTER);
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
