package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.messages.GUIEditMessage;
import com.danicode.microblogging.gui.messages.GUIPostMessage;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Posee dos constructores, según el que uses, el usuario podrá publicar un nuevo mensaje o editar un mensaje existente.
 */
public class PostEditMessageController implements DocumentListener {
    private final MessageService messageService;
    private final UserService userService;
    private GUIPostMessage postMessage;
    private Message messageToUpdate;

    /**
     * Inicializa los servicios necesarios.
     * @see UserService
     * @see MessageService
     */
    private PostEditMessageController() {
        this.messageService = new MessageService();
        this.userService = new UserService();
    }

    /**
     * Con este constructor, el usuario podrá publicar un nuevo mensaje
     * @param owner Ventana padre
     */
    public PostEditMessageController(Window owner) {
        this();
        this.postMessage = new GUIPostMessage(owner);
        this.init();
    }

    /**
     * Con este constructor, debes especificar cuál mensaje quiere editar el usuario
     * @param owner Ventana padre
     * @param messageToUpdate Mensaje a actualizar
     */
    public PostEditMessageController(Window owner, Message messageToUpdate) {
        this();
        this.messageToUpdate = messageToUpdate;
        this.postMessage = new GUIEditMessage(owner, messageToUpdate);
        this.init();
    }

    /**
     * Confirma los datos y verifica si estás en modo edición o publicación.
     * @param message Mensaje de confirmación.
     * @see #checkAction(boolean, String)
     */
    private void confirm(String message) {
        this.postMessage.getJPost().setText("");

        JOptionPane.showMessageDialog(null, "¡"  + message + "!");

        if (this.messageToUpdate == null) {
            var option = JOptionPane.showConfirmDialog(null, "¿Deseas publicar otro?",
                    "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option != JOptionPane.YES_OPTION) {
                this.postMessage.dispose();
            }
        }
        else {
            this.postMessage.dispose();
        }
    }

    /**
     * Inserta o actualiza un mensaje, muestra un mensaje de acuerdo a si ocurre o no algún error.
     * @param action Preferible usar algún método que ejecute una acción y devuelva un boolean según si hubo o no algún error.
     * @param message Mensaje de confirmación.
     */
    private void checkAction(boolean action, String message) {
        if (action) {
            this.confirm(message);
        }
        else {
            JOptionPane.showMessageDialog(null, "Algo salió mal");
        }
    }

    /**
     * Publica o edita un mesanje seǵun el constructor utilizado.
     */
    private void postMessage() {
        var messageText = this.postMessage.getJPost().getText().strip();

        if (messageText.length() >= 1 && messageText.length() <= 140) {
            var message = new Message(this.userService.getUserLogged(), messageText);
            var edit = this.messageToUpdate != null;
            if (edit) message.setIdMessage(this.messageToUpdate.getIdMessage());

            this.checkAction(edit ? this.messageService.editMessage(message) : this.messageService.createMessage(message),
                    edit ? "Mensaje actualizado con éxito" : "Mensaje publicado con éxito");
        }
        else {
            JOptionPane.showMessageDialog(null, "El mensaje debe tener entre 1 y 140 caracteres");
        }
    }

    /**
     * Establece las acciones de los botones y del área de texto.
     */
    private void setActions() {
        var document = this.postMessage.getJPost().getDocument();
        document.addDocumentListener(this);

        this.postMessage.getBExit().addActionListener(e -> this.postMessage.dispose());
        this.postMessage.getBPost().addActionListener(e -> this.postMessage());
    }

    /**
     * Inicializa los métodos necesarios.
     */
    private void init() {
        this.setActions();
    }

    /**
     * Calcula los caracteres restantes y/o sobrantes y los muestra en un jlabel.
     */
    private void checkCharacters() {
        var length = this.postMessage.getJPost().getText().strip().length();
        var remaining = 0;

        if (length > 140) {
            remaining = - (this.postMessage.POST_LENGTH - length);
            this.postMessage.getLCharacters().setText("Límite excedido: " + remaining);
            this.postMessage.getLCharacters().setForeground(Color.RED);
        }
        else {
            remaining = this.postMessage.POST_LENGTH - length;
            this.postMessage.getLCharacters().setText(this.postMessage.CHARACTERS_REMAINING + remaining);
            this.postMessage.getLCharacters().setForeground(Color.DARK_GRAY);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        this.checkCharacters();
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        this.checkCharacters();
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
    }
}
