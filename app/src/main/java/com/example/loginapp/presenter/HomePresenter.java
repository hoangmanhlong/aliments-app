package com.example.loginapp.presenter;

import com.example.loginapp.model.interator.HomeInterator;
import com.example.loginapp.model.listener.HomeListener;
import com.example.loginapp.view.fragment.HomeView;

public class HomePresenter implements HomeListener {
    private HomeView view;
    private HomeInterator interator;

    public HomePresenter(HomeView view) {
        this.view = view;
        interator = new HomeInterator(this);
    }

    public void logout() {
        interator.logout();
    }

    @Override
    public void goOverviewScreen() {
        view.goOverviewScreen();
    }

    @Override
    public void onLogoutMessage(String message) {
        view.onLogoutMessage(message);
    }
}
