package com.example.waterquality.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;

@Route("")
@PageTitle("Login")
@CssImport("./styles.css")
public class LoginView extends VerticalLayout {

    public LoginView() {
        addClassName("wrapper");
        addClassName("login-bg");

        Div loginBox = new Div();
        loginBox.addClassName("login-box");

        // Login Header
        Div loginHeader = new Div();
        loginHeader.addClassName("login-header");
        loginHeader.add(new Span("Login"));
        loginBox.add(loginHeader);

        // Username Field
        Div usernameWrapper = new Div();
        usernameWrapper.addClassName("input-box");
        TextField username = new TextField();
        username.setPlaceholder("Username");
        username.addClassName("input-field");
        usernameWrapper.add(username);
        loginBox.add(usernameWrapper);

        // Password Field
        Div passwordWrapper = new Div();
        passwordWrapper.addClassName("input-box");
        PasswordField password = new PasswordField();
        password.setPlaceholder("Password");
        password.addClassName("input-field");
        passwordWrapper.add(password);
        loginBox.add(passwordWrapper);

        // Remember Me & Forgot Password
        Div rememberForgot = new Div();
        rememberForgot.addClassName("remember-forgot");
        Checkbox rememberMe = new Checkbox("Remember me");
        Button forgotPassword = new Button("Forgot Password?");
        rememberForgot.add(rememberMe, forgotPassword);
        loginBox.add(rememberForgot);

        // Login Button
        Button loginButton = new Button("Login", VaadinIcon.SIGN_IN.create());
        loginButton.addClassName("input-submit");
        loginButton.addClickListener(event -> {
            if (authenticate(username.getValue(), password.getValue())) {
                UI.getCurrent().navigate("main");
            } else {
                username.setInvalid(true);
                password.setInvalid(true);
            }
        });

        loginBox.add(loginButton);
        add(loginBox);
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}