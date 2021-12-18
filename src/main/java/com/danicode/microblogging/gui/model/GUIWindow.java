package com.danicode.microblogging.gui.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class GUIWindow extends JFrame {
    /**
     * No es necesario usar el método setVisible(true).
     * Crea una ventana personalizable.
     * Sobreescribe el método close() para dar el comportamiento al momento
     * de hacer click en el botón de cerrar (x).
     * Sobreescribe el método init() para inicializar todos los componentes de
     * la ventana y sus métodos.
     * No es necesario llamar a éstos métodos abstractos ya que ya están siendo llamados,
     * por lo que con sólo sobreescribirlos funciona correctamente.
     * @param width ancho
     * @param height alto
     * @param title título
     * @param resizable tamaño ajustable
     * @param layout layout por defecto
     */
    public GUIWindow(int width, int height, String title, boolean resizable, LayoutManager layout) {
        super(title);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(resizable);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) { GUIWindow.this.close(); }
        });
        this.getContentPane().setLayout(layout);
        this.init();
        this.setVisible(true);
    }

    /**
     * Define el comportamiento que se ejecutará al momento de hacer click
     * en el botón superior derecho de la ventana para cerrar (X).
     */
    protected abstract void close();

    /**
     * Especifica todos los componentes gráficos y métodos para que la ventana
     * se ejecute correctamente.
     */
    protected abstract void init();
}
