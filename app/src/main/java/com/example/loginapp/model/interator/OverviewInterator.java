package com.example.loginapp.model.interator;

import com.example.loginapp.App;
import com.example.loginapp.data.AppSharedPreferences;
import com.example.loginapp.model.listener.OverviewListener;
import com.example.loginapp.presenter.OverviewPresenter;

public class OverviewInterator {
    private OverviewListener listener;

    public OverviewInterator(OverviewListener listener) {
        this.listener = listener;
    }

    public void isLogged() {
        if (AppSharedPreferences.getInstance(App.getInstances().getApplicationContext()).getLoginStatus()) {
            listener.goHomeScreen();
        }
    }
}
