package com.danicode.microblogging.controllers;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.gui.mainmenu.GUIAboutUs;
import com.danicode.microblogging.gui.mainmenu.GUIMainMenu;
import com.danicode.microblogging.services.MessageService;
import com.danicode.microblogging.services.UserService;

import javax.swing.*;

public class MainMenuController {
    private final GUIMainMenu template;
    private final UserService userService;

    public MainMenuController() {
        this.template = new GUIMainMenu();
        this.userService = new UserService();
        this.setActions();
    }

    private void logOut() {
        this.userService.resetUserLogged();
        new LogInController();
        this.template.dispose();
    }

    private void deletePosts() {
        var service = new MessageService();
        var size = service.getMessages(BlogConstants.LIST_MY_MESSAGES, null).size();
        if (size < 1) {
            JOptionPane.showMessageDialog(null,
                    "No tienes mensajes publicados, prueba a publicar uno en la sección Posting -> Publicar");
        }
        else {
            var message = size > 1 ? " mensajes" : " mensaje";
            var consult = "Tienes " + size + message + ".\n¿Deseas borrarlos todos?\nNo podrás recuperarlos.";
            var option = JOptionPane.showConfirmDialog(null, consult, "Consulta",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                var deleted = service.deleteUserMessages();
                var confirm = deleted ? "¡Todos sus mensajes han sido eliminados correctamente!" : "Ha habido un error";
                JOptionPane.showMessageDialog(null, confirm);
            }
        }
    }

    private void setActions() {
        this.template.getAboutUs().addActionListener(e -> new GUIAboutUs(this.template));
        this.template.getProfileVisualize().addActionListener(e -> new ViewProfileController(this.template, this.userService.getUserLogged()));
        this.template.getFileExit().addActionListener(e -> System.exit(0));
        this.template.getPostingPost().addActionListener(e -> new PostEditMessageController(this.template));
        this.template.getPostingSurf().addActionListener(e -> new ViewMessagesController(this.template));
        this.template.getProfileEdit().addActionListener(e -> new EditProfileController(this.template, this.userService.getUserLogged()));
        this.template.getPostingDelete().addActionListener(e -> this.deletePosts());
        this.template.getFileLogOut().addActionListener(e -> this.logOut());
    }
}
