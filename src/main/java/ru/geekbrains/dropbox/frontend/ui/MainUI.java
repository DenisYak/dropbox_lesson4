package ru.geekbrains.dropbox.frontend.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.dropbox.frontend.service.FilesService;
import ru.geekbrains.dropbox.frontend.ui.view.LoginView;
import ru.geekbrains.dropbox.frontend.ui.view.MainView;


import java.io.*;

@SpringUI
@PushStateNavigation
public class MainUI extends UI {

    @Autowired
    FilesService filesService;

    Navigator navigator;

    @Override
    public void init(VaadinRequest request) {

        navigator = new Navigator(this, this);
        navigator.addView("main", MainView.class);
        navigator.addView("login", LoginView.class);
//        navigator.navigateTo("main");

    }
}
