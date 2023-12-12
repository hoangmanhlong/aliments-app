package com.example.loginapp.presenter;

import com.example.loginapp.model.interator.OverviewInterator;
import com.example.loginapp.model.listener.OverviewListener;
import com.example.loginapp.view.fragment.OverviewView;

public class OverviewPresenter implements OverviewListener {
    private OverviewView view;

    private OverviewInterator overviewInterator;

    public OverviewPresenter(OverviewView view) {
        this.view = view;
        overviewInterator = new OverviewInterator(this);

    }

    @Override
    public void isLogged() {
        overviewInterator.isLogged();
    }

    @Override
    public void goHomeScreen() {
        view.goHomeScreen();
    }
}
