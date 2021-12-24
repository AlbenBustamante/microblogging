package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIViewMessages extends GUIDialog {
    private JPanel northPane, centerPane, southPane;
    private JTextField tfSearch;
    private JComboBox<String> cbSearchType, cbOrderType;
    private JButton bSearch, bExit, bPrevious, bNext;
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
        this.centerPane.setPreferredSize(new Dimension(this.getContentPane().getWidth(), 2655));
        this.scCenterPane = new JScrollPane(this.centerPane);
        this.scCenterPane.setBorder(null);

        this.southPane = new JPanel();
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 18, 10));
        this.southPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    }

    private void createFields() {
        this.tfSearch = new JTextField("Buscar mensajes...", 20);
    }

    private void createComboBoxes() {
        this.cbSearchType = new JComboBox<>(new String[] {
                BlogConstants.SEARCH_MESSAGES, BlogConstants.SEARCH_BY_MESSAGE, BlogConstants.SEARCH_MESSAGE_BY_USER,
                BlogConstants.SEARCH_BY_USER_LOGGED
        });

        this.cbOrderType = new JComboBox<>(new String[] {
                BlogConstants.ORDER_BY_NEW_MESSAGES, BlogConstants.ORDER_BY_OLD_MESSAGES
        });
    }

    private void createButtons() {
        this.bSearch = new JButton("Buscar");
        this.bExit = new JButton("Regresar");
        this.bPrevious = new JButton("<<");
        this.bNext = new JButton(">>");
    }

    private void design() {
        this.northPane.add(this.tfSearch);
        this.northPane.add(this.cbSearchType);
        this.northPane.add(this.bSearch);

        this.southPane.add(this.cbOrderType);
        this.southPane.add(this.bPrevious);
        this.southPane.add(this.bNext);
        this.southPane.add(this.bExit);

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
        this.getContentPane().add(this.scCenterPane, BorderLayout.CENTER);
        this.getContentPane().add(this.southPane, BorderLayout.SOUTH);
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

    public JComboBox<String> getCbOrderType() { return this.cbOrderType; }

    public JButton getBSearch() { return this.bSearch; }

    public JButton getBExit() { return this.bExit; }

    public JButton getBPrevious() { return this.bPrevious; }

    public JButton getBNext() { return this.bNext; }

    public JPanel getCenterPane() { return this.centerPane; }

}
