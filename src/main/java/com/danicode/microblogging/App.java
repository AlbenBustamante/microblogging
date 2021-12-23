package com.danicode.microblogging;

import com.danicode.microblogging.controllers.LogInController;

import javax.swing.*;

/**
 * Este es el punto de entrada de éste gran software.
 * <p>Si tienes alguna sugerencia, no dudes en dejármelo saber a través del
 * repositorio de GitHub.</p>
 * <p>Muchas gracias por usarlo.</p>
 * @author alnicode32
 * @version 1.0
 * @since December, 2021
 */
public class App {
    public static void main(String... alnicode32) {
        SwingUtilities.invokeLater(LogInController::new);
    }
}
