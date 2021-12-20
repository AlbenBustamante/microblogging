package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.model.GUIDialog;

import javax.swing.*;
import java.awt.*;

public class GUIPostMessage extends GUIDialog implements BlogConstants {
    private JLabel lPost, lCharacters, lBlank;
    private JTextArea jtaPost;
    private JScrollPane sPost;
    private JButton bPost, bExit;
    private JPanel northPane, centerPane, southPane;

    public GUIPostMessage(JFrame owner) {
        super(owner, 500, 320, "Mensaje a publicar", false, false, new BorderLayout());
    }

    private void createLabels() {
        this.lPost = new JLabel("<html><p align='center';>" +
                "Escribe tu mensaje y postéalo<br/>Máx. 140 caracteres" +
                "</p></html>");
        this.lPost.setHorizontalAlignment(SwingConstants.CENTER);

        this.lBlank = new JLabel("    ");
        this.lBlank.setPreferredSize(new Dimension(500, 0));

        this.lCharacters = new JLabel(CHARACTERS_REMAINING + POST_LENGTH);
    }

    private void createTextArea() {
        this.jtaPost = new JTextArea(5, 42);
        this.jtaPost.setWrapStyleWord(true);
        this.jtaPost.setLineWrap(true);
        this.sPost = new JScrollPane(this.jtaPost);
    }

    private void createButtons() {
        this.bPost = new JButton("Publicar");
        this.bExit = new JButton("Salir");
    }

    private void createPanels() {
        this.northPane = new JPanel();
        this.centerPane = new JPanel();
        this.southPane = new JPanel();

        this.northPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        this.centerPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        this.southPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    }

    private void design() {
        this.northPane.add(this.lPost);

        this.centerPane.add(this.sPost);
        this.centerPane.add(this.lCharacters);
        this.centerPane.add(this.lBlank);
        this.centerPane.add(this.bPost);

        this.southPane.add(this.bExit);

        this.getContentPane().add(this.northPane, BorderLayout.NORTH);
        this.getContentPane().add(this.centerPane, BorderLayout.CENTER);
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
        this.createTextArea();
        this.createButtons();
        this.design();
    }

    public JTextArea getJPost() { return this.jtaPost; }

    public JButton getBPost() { return this.bPost; }

    public JButton getBExit() { return this.bExit; }

    public JLabel getLCharacters() { return this.lCharacters; }
}
