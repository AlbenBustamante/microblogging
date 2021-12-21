package com.danicode.microblogging.controllers;

import com.danicode.microblogging.gui.mainmenu.GUIAboutUs;
import com.danicode.microblogging.gui.mainmenu.GUIMainMenu;
import com.danicode.microblogging.services.UserService;

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

    private void setActions() {
        this.template.getAboutUs().addActionListener(e -> new GUIAboutUs(this.template));
        this.template.getProfileVisualize().addActionListener(e -> new ViewProfileController(this.template, this.userService.getUserLogged()));
        this.template.getFileExit().addActionListener(e -> System.exit(0));
        this.template.getPostingPost().addActionListener(e -> new PostEditMessageController(this.template));
        this.template.getPostingSurf().addActionListener(e -> new ViewMessagesController(this.template));
        this.template.getProfileEdit().addActionListener(e -> new EditProfileController(this.template, this.userService.getUserLogged()));
        this.template.getFileLogOut().addActionListener(e -> this.logOut());
    }
}
