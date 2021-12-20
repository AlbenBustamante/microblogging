package com.danicode.microblogging.gui.messages;

import javax.swing.*;

public class GUIEditMessage extends GUIPostMessage {
    public GUIEditMessage(JFrame owner) {
        super(owner);
        this.changeTexts();
    }

    private void changeTexts() {
        this.getLPost().setText("<html><p align='center';>" +
                "Escribe tu mensaje y actualízalo<br/>Máx. 140 caracteres" +
                "</p></html>");
        this.getBPost().setText("Actualizar");
    }
}
