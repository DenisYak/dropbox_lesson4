package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.geekbrains.dropbox.frontend.service.FilesService;


import javax.annotation.PostConstruct;

@SpringView(name = "login")
public class LoginView extends VerticalLayout implements View {

    @Autowired
    private FilesService filesService;

    public LoginView() {
        TextField login = new TextField();
        login.setPlaceholder("Login");
        PasswordField pass = new PasswordField();
        pass.setPlaceholder("Password");
        Button btnLogin = new Button("Login", clickEvent -> {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(login.getValue(), pass.getValue());
            SecurityContextHolder.getContext().setAuthentication(token);
//            navigator.navigateTo("main");
            getUI().getNavigator().navigateTo("");
        });
        addComponents(login, pass, btnLogin);
//        getUI().setContent(this);


    }

}
