package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.users.GUIEditProfile;
import com.danicode.microblogging.model.domain.User;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Mediante esta clase, gestionas los cambios que puede hacer el usuario a su propio perfil
 * */
public class EditProfileController {
    private final GUIEditProfile template;
    private final UserService service;
    private final User user;
    private String name, lastName, email, password;

    /**
     * Con éste constructor, haces visible la interfaz gráfica de usuario para editar un perfil de usuario.
     * <p>Es necesario pasarle el usuario a editar por parámetro.</p>
     * @see com.danicode.microblogging.gui.users.GUIEditProfile
     * @see com.danicode.microblogging.services.UserService
     * @param owner Hace referencia a la ventana padre.
     * @param user Es el usuario a editar su información.
     * */
    public EditProfileController(JFrame owner, User user) {
        this.template = new GUIEditProfile(owner);
        this.service = new UserService();
        this.user = user;
        this.init();
    }

    /**
     * Reemplaza el texto de los labels por información del usuario.
     * */
    private void setTexts() {
        this.template.getLUsername().setText("@" + this.user.getUsername() + " #" + this.user.getIdUser());
        this.template.getTfName().setText(this.user.getName());
        this.template.getTfLastName().setText(this.user.getLastName());
        this.template.getTfEmail().setText(this.user.getEmail());
    }

    /**
     * Obtiene la información escrita por el usuario en los campos de texto y los guarda en su respectiva variable.
     * */
    private void setData() {
        this.name = this.template.getTfName().getText().strip();
        this.lastName = this.template.getTfLastName().getText().strip();
        this.email = this.template.getTfEmail().getText().strip();
        this.password = new String(this.template.getPfPassword().getPassword());
    }

    /**
     * Chequea condiciones y si los datos están bien, actualiza el usuario en la base de datos.
     * */
    private void apply() {
        this.setData();
        var isBlank = this.name.equals("") || this.lastName.equals("") || this.email.equals("") || this.password.equals("");
        var message = "";

        if (!isBlank) {
            var newUser = new User(this.user.getIdUser(), this.name, this.lastName, email, this.user.getUsername(), this.password);
            message = this.service.updateUser(newUser) ? "Usuario actualizado correctamente" : "Algo ha ocurrido mal";
        }
        else {
            message = "Por favor, llena todos los campos";
        }

        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Pide una confirmación al usuario, si está de acuerdo, borrará al usuario de la base de datos.
     * */
    private void delete() {
        var option = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas borrar tu perfil?\n" +
                        "@" + this.user.getUsername(), "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            var deleted = this.service.deleteUser(this.user.getIdUser());
            var message = deleted ? "¡Usuario borrado correctamente!" : "Algo ha ocurrido mal";
            JOptionPane.showMessageDialog(null, message);

            if (deleted) {
                this.service.resetUserLogged();
                new LogInController();
                this.template.getOwner().dispose();
                this.template.dispose();
            }
        }
    }

    /**
     * Establece las acciones para los campos de texto y botones.
     * */
    private void setActions() {
        this.setActions(e -> this.apply(), this.template.getTfName(), this.template.getTfLastName(),
                this.template.getTfEmail(), this.template.getPfPassword());
        this.template.getBDelete().addActionListener(e -> this.delete());
        this.template.getBAccept().addActionListener(e -> this.apply());
        this.template.getBCancel().addActionListener(e -> this.template.dispose());
    }

    /**
     * Es un método que simplifica líneas de código al implementar el mismo evento a varios campos de texto y/o contraseña.
     * */
    private void setActions(ActionListener event, JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.addActionListener(event));
    }

    /**
     * Inicializa todos los métodos para que funcione correctamente el controlador.
     * */
    private void init() {
        this.setActions();
        this.setTexts();
    }
}
