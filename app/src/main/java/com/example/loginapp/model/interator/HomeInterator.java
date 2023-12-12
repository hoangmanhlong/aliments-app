package com.example.loginapp.model.interator;

import com.example.loginapp.App;
import com.example.loginapp.data.AppSharedPreferences;
import com.example.loginapp.model.listener.HomeListener;

public class HomeInterator {

    private HomeListener listener;

    public HomeInterator(HomeListener listener) {
        this.listener = listener;
    }

    public void logout() {
        AppSharedPreferences.getInstance(App.getInstances().getApplicationContext()
            .getApplicationContext()).setLoginStatus(false);
        listener.goOverviewScreen();
        listener.onLogoutMessage("Signed out successfully");
    }
}
