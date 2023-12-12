package com.example.loginapp.presenter;

import com.example.loginapp.model.interator.RegisterInterator;
import com.example.loginapp.model.listener.RegisterListener;
import com.example.loginapp.view.fragment.RegisterView;

public class RegisterPresenter implements RegisterListener {
    private RegisterInterator interator;
    private RegisterView view;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        interator = new RegisterInterator(this);
    }

    public void register(String email, String password, String confirmPassword) {
        interator.register(email, password, confirmPassword);
    }

    public void goLoginScreen() {
        view.goLoginScreen();
    }

    @Override
    public void onRegisterMessage(String message) {
        view.onRegisterMessage(message);
    }
}
