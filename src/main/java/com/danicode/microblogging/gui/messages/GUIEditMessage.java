package com.danicode.microblogging.gui.messages;

import com.danicode.microblogging.model.domain.Message;

import javax.swing.*;
import java.awt.*;

public class GUIEditMessage extends GUIPostMessage {
    private final Message messageToUpdate;
    public GUIEditMessage(Window owner, Message messageToUpdate) {
        super(owner);
        this.messageToUpdate = messageToUpdate;
        this.changeTexts();
    }

    private void changeTexts() {
        this.getLPost().setText("<html><p align='center';>" +
                "Escribe tu mensaje y actualízalo<br/>Máx. 140 caracteres" +
                "</p></html>");
        this.getBPost().setText("Actualizar");
        this.getJPost().setText(this.messageToUpdate.getMessage());
    }
}
