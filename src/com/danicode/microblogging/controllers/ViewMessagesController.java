package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.messages.GUIViewMessages;
import com.danicode.microblogging.gui.messages.ViewPostTemplate;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Esta clase se encarga de toda la lógica para mostrar los mensajes.
 */
public class ViewMessagesController {
    private int currentPage = 1, totalPages;
    private List<Message> messages;
    private JLabel lPageIndex;
    private final GUIViewMessages messagesTemplate;
    private final MessageService service;
    private final User userLogged;
    private final ViewPostTemplate[] postTemplate;
    private static final int MAX_MESSAGES = 10;

    /**
     * Muestra la interfaz gráfica de mensajes e interactúa con los usuarios y la base de datos.
     * @param owner Ventana padre
     * @see GUIViewMessages
     * @see MessageService
     * @see UserService
     * @see ViewPostTemplate
     */
    public ViewMessagesController(JFrame owner) {
        this.messagesTemplate = new GUIViewMessages(owner);
        this.service = new MessageService();
        this.userLogged = new UserService().getUserLogged();
        this.messages = this.service.sortedMessages(BlogConstants.ORDER_BY_NEW_MESSAGES, BlogConstants.LIST_MESSAGES, null);
        this.postTemplate = new ViewPostTemplate[MAX_MESSAGES];
        this.init();
    }

    /**
     * Establece el número total de páginas según la cantidad de mensajes almacenados.
     */
    private void setTotalPages() {
        this.totalPages = 0;
        for (int i = 0; i < this.messages.size(); i ++) {
            if (i % MAX_MESSAGES == 0) {
                this.totalPages ++;
            }
        }
    }

    /**
     * Indica mediante un label la página actual y la cantidad total de páginas.
     */
    private void resetLabel() {
        this.lPageIndex.setText("Página " + this.currentPage + " de " + this.totalPages);
    }

    /**
     * Crea y muestra el indicador de páginas
     */
    private void createLabel() {
        this.lPageIndex = new JLabel("Página " + this.currentPage + " de " + this.totalPages);
        this.messagesTemplate.getCenterPane().add(this.lPageIndex);
    }

    /**
     * Muestra los datos de un mensaje en un postTemplate
     * @param index Índice del postTemplate.
     * @param message Mensaje de donde se obtendrán los datos.
     */
    private void setData(int index, Message message) {
        this.postTemplate[index].getLFullName().setText(message.getUser().getName() + " " + message.getUser().getLastName());
        this.postTemplate[index].getLUsername().setText("@" + message.getUser().getUsername());
        this.postTemplate[index].getLDateTime().setText(message.getDateTime());
        this.postTemplate[index].getTaMessage().setText(message.getMessage());
    }

    /**
     * Carga los primeros 10 mensajes según el filtro indicado en el constructor y los muestra.
     */
    private void loadMainData() {
        for (int i = 0; i < MAX_MESSAGES; i ++) {
            this.postTemplate[i] = new ViewPostTemplate();
            this.postTemplate[i].addMouseListener(this.popupAction(i));
            this.setActions(i);
            this.messagesTemplate.getCenterPane().add(this.postTemplate[i]);
            try {
                this.setData(i, this.messages.get(i));
            } catch (IndexOutOfBoundsException ex) {
                this.postTemplate[i].setVisible(false);
            }
        }
    }

    /**
     * Resetea la página actual a 1, en caso de que al cargar una lista de mensajes, la página actual sea superior al
     * número total de páginas.
     */
    private void checkCurrentPage() {
        if (this.currentPage > this.totalPages) {
            this.currentPage = 1;
        }
    }

    /**
     * Actualiza todos los datos y los postTemplate.
     */
    private void refreshPage() {
        this.setTotalPages();
        this.checkCurrentPage();

        var elements = (this.currentPage - 1) * MAX_MESSAGES;

        for (int i = elements, j = 0; j < MAX_MESSAGES; i ++, j ++) {
            try {
                this.setData(j, this.messages.get(i));
                this.postTemplate[j].setVisible(true);
            } catch (IndexOutOfBoundsException ex) {
                this.postTemplate[j].setVisible(false);
            }
        }

        this.resetLabel();
    }

    /**
     * Incrementa la página actual y actualiza los datos.
     */
    private void nextPage() {
        if (this.currentPage < this.totalPages) {
            this.currentPage ++;
            this.refreshPage();
        }
    }

    /**
     * Decrementa la página actual y actualiza los datos.
     */
    private void previousPage() {
        if (this.currentPage > 1) {
            this.currentPage --;
            this.refreshPage();
        }
    }

    /**
     * Según la opción seleccionada por el usuario en {@code cbSearchType}, establece el filtro a utilizar
     * para actualizar los datos.
     */
    private void checkSearchResult() {
        var optionSelected = String.valueOf(this.messagesTemplate.getCbSearchType().getSelectedItem());
        if (optionSelected.equals(BlogConstants.SEARCH_BY_MESSAGE)) {
            this.search(BlogConstants.LIST_BY_MESSAGE);
        }
        else if (optionSelected.equals(BlogConstants.SEARCH_MESSAGE_BY_USER)) {
            this.search(BlogConstants.LIST_USER_MESSAGES);
        }
        else if (optionSelected.equals(BlogConstants.SEARCH_BY_USER_LOGGED)) {
            this.search(BlogConstants.LIST_MY_MESSAGES);
        }
        else {
            this.search(BlogConstants.LIST_MESSAGES);
        }
    }

    /**
     * Según el filtro seleccionado por el usuario, actualiza la lista de mensajes y los muestra en los postTemplate.
     * @param filter Preferible usar BlogConstants para los filtros, tienen como sufijo {@code LIST}.
     */
    private void search(int filter) {
        var text = this.messagesTemplate.getTfSearch().getText().strip();
        var message = new Message();

        if (filter == BlogConstants.LIST_USER_MESSAGES) {
            message.setUser(new User("", "", "", text, ""));
        } else if (filter == BlogConstants.LIST_MY_MESSAGES) {
            message.setUser(this.userLogged);
        } else if (filter == BlogConstants.LIST_BY_MESSAGE) {
            message.setMessage(text);
        }

        var order = String.valueOf(this.messagesTemplate.getCbOrderType().getSelectedItem());
        this.messages = service.sortedMessages(order, filter, message);

        if (!this.messages.isEmpty()) {
            this.refreshPage();
        }
        else {
            JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
        }
    }

    /**
     * Es una acción para que al hacer click en (preferiblemente un postTemplate), muestre las opciones
     * que puede usar el usuario en un mensaje, sobretodo si pertenece al usuario logueado.
     * @param index Índice del postTemplate.
     * @return Devuelve la acción
     */
    private MouseAdapter popupAction(int index) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                postTemplate[index].getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
        };
    }

    /**
     * Permite ver el perfil de cualquier usuario.
     * @param index Índice del postTemplate.
     */
    private void viewProfile(int index) {
        var username = this.postTemplate[index].getLUsername().getText().split("@");
        var profile = this.service.getAuthor(username[1]);
        new ViewProfileController(this.messagesTemplate, profile);
    }

    /**
     * Verifica que el nombre de usuario del postTemplate, coincida con el mismo del usuario logueado.
     * @param index Índice del postTemplate.
     * @return Devuelve true si los datos coinciden.
     */
    private boolean isUserLoggedMessage(int index) {
        var username = this.postTemplate[index].getLUsername().getText().split("@")[1];
        return this.userLogged.getUsername().equals(username);
    }

    /**
     * Permite editar un mensaje si {@code isUserLoggedMessage} devuelve true.
     * @param index Índice del postTemplate.
     */
    private void editMessage(int index) {
        if (this.isUserLoggedMessage(index)) {
            var dateTime = this.postTemplate[index].getLDateTime().getText();
            var messageToEdit = this.service.getMessage(dateTime);
            new PostEditMessageController(this.messagesTemplate, messageToEdit);
        }
        else {
            JOptionPane.showMessageDialog(null, "Sólo puedes editar tus propios mensajes",
                    this.userLogged.getUsername(), JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Permite borrar un mensaje del usuario logueado, es decir, si {@code isUserLoggedMessage} devuelve true.
     * @param index Índice del postTemplate.
     */
    private void deleteMessage(int index) {
        if (this.isUserLoggedMessage(index)) {
            var dateTime = this.postTemplate[index].getLDateTime().getText();
            var idMessage = this.service.getMessage(dateTime).getIdMessage();

            var option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el mensaje?",
                    "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                var confirm = this.service.deleteMessage(idMessage) ?
                        "¡Mensaje eliminado correctamente!" : "Algo ocurrió mal";
                JOptionPane.showMessageDialog(null, confirm);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Sólo puedes eliminar tus propios mensajes",
                    this.userLogged.getUsername(), JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Establece las acciones del PopupMenu de algún postTemplate.
     * @param index Índice del postTemplate.
     */
    private void setActions(int index) {
        this.postTemplate[index].getMenuViewProfile().addActionListener(e -> this.viewProfile(index));
        this.postTemplate[index].getMenuEditMessage().addActionListener(e -> this.editMessage(index));
        this.postTemplate[index].getMenuDeleteMessages().addActionListener(e -> this.deleteMessage(index));
    }

    /**
     * Estblaece las acciones de cada botón y el campo de texto para buscar.
     */
    private void setActions() {
        this.messagesTemplate.getBExit().addActionListener(e -> this.messagesTemplate.dispose());
        this.messagesTemplate.getBNext().addActionListener(e -> this.nextPage());
        this.messagesTemplate.getBPrevious().addActionListener(e -> this.previousPage());
        this.messagesTemplate.getBSearch().addActionListener(e -> this.checkSearchResult());
        this.messagesTemplate.getTfSearch().addActionListener(e -> this.checkSearchResult());
    }

    /**
     * Inicializa los métodos necesarios para iniciar el controlador.
     */
    private void init() {
        this.setTotalPages();
        this.setActions();
        this.loadMainData();
        this.createLabel();
    }
}
