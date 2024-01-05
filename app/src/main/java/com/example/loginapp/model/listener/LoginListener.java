package com.example.loginapp.model.listener;

public interface LoginListener {
    void goHomeScreen();
    void onLoginMessage(String message);

    void onShowProcessBar(Boolean show);
}
