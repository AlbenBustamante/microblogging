package com.danicode.gui.model;

import java.awt.Window;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public abstract class GUIDialog extends JDialog {
    public GUIDialog(Window owner, int width, int height, String title, boolean resizable, boolean modal,
                     LayoutManager layout) {
        super(owner, title);
        this.setModal(modal);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) { GUIDialog.this.close(); }
        });
        this.getContentPane().setLayout(layout);
        this.init();
        this.setVisible(true);
    }

    protected abstract void close();

    protected abstract void init();
}
