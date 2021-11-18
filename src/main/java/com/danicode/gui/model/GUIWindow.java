package com.danicode.gui.model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public abstract class GUIWindow extends JFrame {
    public GUIWindow(int width, int height, String title, boolean resizable) {
        super(title);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) { GUIWindow.this.close(); }
        });
        this.init();
        this.setVisible(true);
    }

    protected abstract void close();

    protected abstract void init();
}
