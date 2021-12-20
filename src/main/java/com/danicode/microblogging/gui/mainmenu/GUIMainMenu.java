package com.danicode.microblogging.gui.mainmenu;

import com.danicode.microblogging.gui.model.GUIWindow;

import javax.swing.*;
import java.awt.*;

public class GUIMainMenu extends GUIWindow {
    private JMenuBar menuBar;
    private JMenu menuFile, menuProfile, menuPosting, menuAbout;
    private JMenuItem mFileLogOut, mFileExit, mProfileEdit, mProfileVisualize, mPostingSurf, mPostingPost, mAboutUs;

    public GUIMainMenu() {
        super(380, 240, "Microblogging 1.0", true, new FlowLayout(FlowLayout.CENTER, 0, 80));
    }

    private void createMenu() {
        //Barra de menu
        this.menuBar = new JMenuBar();
        //Menu
        this.menuFile = new JMenu("Archivo");
        this.menuProfile = new JMenu("Mi Perfil");
        this.menuPosting = new JMenu("Posting");
        this.menuAbout = new JMenu("Acerca de");
        //Items de cada menu
        this.mFileLogOut = new JMenuItem("Cerrar sesión");
        this.mFileExit = new JMenuItem("Salir");
        this.mProfileEdit = new JMenuItem("Editar");
        this.mProfileVisualize = new JMenuItem("Visualizar");
        this.mPostingSurf = new JMenuItem("Navegar");
        this.mPostingPost = new JMenuItem("Publicar");
        this.mAboutUs = new JMenuItem("microblogging");
    }

    private void setItems() {
        //Menu File
        this.menuFile.add(this.mFileLogOut);
        this.menuFile.add(this.mFileExit);
        //Menu Profile
        this.menuProfile.add(this.mProfileEdit);
        this.menuProfile.add(this.mProfileVisualize);
        //Menu Posting
        this.menuPosting.add(this.mPostingSurf);
        this.menuPosting.add(this.mPostingPost);
        //Menu About
        this.menuAbout.add(this.mAboutUs);
    }

    private void setMenus() {
        this.menuBar.add(this.menuFile);
        this.menuBar.add(this.menuProfile);
        this.menuBar.add(this.menuPosting);
        this.menuBar.add(this.menuAbout);
    }

    private void createLabel() {
        final var welcome = "¡Bienvenido a Microblogging 1.0!";
        final var lWelcome = new JLabel(welcome);
        this.getContentPane().add(lWelcome);
    }

    @Override
    protected void close() {
        JOptionPane.showMessageDialog(null, "¡Hasta pronto!");
        System.exit(0);
    }

    @Override
    protected void init() {
        this.createLabel();
        this.createMenu();
        this.setItems();
        this.setMenus();
        this.setJMenuBar(this.menuBar);
    }

    public JMenuItem getFileLogOut() { return this.mFileLogOut; }

    public JMenuItem getFileExit() { return this.mFileExit; }

    public JMenuItem getProfileEdit() { return this.mProfileEdit; }

    public JMenuItem getProfileVisualize() { return this.mProfileVisualize; }

    public JMenuItem getPostingSurf() { return this.mPostingSurf; }

    public JMenuItem getPostingPost() { return this.mPostingPost; }

    public JMenuItem getAboutUs() { return this.mAboutUs; }
}
