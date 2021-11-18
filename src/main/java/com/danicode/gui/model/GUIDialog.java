package com.danicode.gui.model;

import java.awt.Window;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public abstract class GUIDialog extends JDialog {
    /**
     * No es necesario usar el método setVisible(true).
     * Crea una ventana de diálogo personalizable.
     * Sobreescribe el método close() para dar el comportamiento al momento
     * de hacer click en el botón de cerrar (x).
     * Sobreescribe el método init() para inicializar todos los componentes de
     * la ventana y sus métodos.
     * No es necesario llamar a éstos métodos abstractos ya que ya están siendo llamados,
     * por lo que con sólo sobreescribirlos funciona correctamente.
     * @param owner ventana padre
     * @param width ancho
     * @param height alto
     * @param title título
     * @param resizable tamaño ajustable
     * @param modal define si quieres que la ventana padre se pueda usar mientras esté ésta ventana abierta o no
     * @param layout layout por defecto
     */
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
