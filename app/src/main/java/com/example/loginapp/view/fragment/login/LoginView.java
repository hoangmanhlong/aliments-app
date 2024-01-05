package com.example.loginapp.view.fragment.login;

public interface LoginView {
    void goHomeScreen();

    void onLoginMessage(String message);

    void onShowProcessBar(Boolean show);

}
