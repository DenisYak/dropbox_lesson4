package ru.geekbrains.dropbox.frontend.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.dropbox.frontend.service.FilesService;
import ru.geekbrains.dropbox.frontend.ui.view.LoginView;
import ru.geekbrains.dropbox.frontend.ui.view.MainView;


import javax.servlet.annotation.WebServlet;
import java.io.*;

@SpringUI
@PushStateNavigation
public class MainUI extends UI {

    @Autowired
//    FilesService filesService;
    private SpringViewProvider viewProvider;

    private Navigator navigator;

    @Override
    public void init(VaadinRequest request) {

        navigator = new Navigator(this, this);
//        navigator.addView("main", MainView.class);
//        navigator.addView("login", LoginView.class);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
//        navigator.navigateTo("main");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MainUIServlet extends VaadinServlet {
    }
}
