package com.example.loginapp.presenter;

import com.example.loginapp.model.interator.LoginInteractor;
import com.example.loginapp.model.listener.LoginListener;
import com.example.loginapp.view.fragment.LoginView;

public class LoginPresenter implements LoginListener {
    private final LoginView view;
    private final LoginInteractor loginInteractor;

    public LoginPresenter(LoginView view) {
        this.view = view;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void goHomeScreen() {
        view.goHomeScreen();
    }

    public void login(String email, String password) {
        loginInteractor.login(email, password);
    }

    @Override
    public void onLoginMessage(String message) {
        view.onLoginMessage(message);
    }
}
