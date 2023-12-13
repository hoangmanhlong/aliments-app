package com.example.loginapp.model.interator;

import android.content.Context;

import com.example.loginapp.App;
import com.example.loginapp.data.AppSharedPreferences;
import com.example.loginapp.model.entity.Account;
import com.example.loginapp.model.listener.LoginListener;

import java.util.List;

public class LoginInteractor {

    private LoginListener listener;

    public LoginInteractor(LoginListener listener) {
        this.listener = listener;
    }

    public void login(
        String email,
        String password
    ) {
        Context context = App.getInstances().getApplicationContext().getApplicationContext();
        if (email.equals("") || password.equals("")) {
            listener.onLoginMessage("Please enter complete information");
        } else {
            if(checkLogin(context, email, password)) {
                AppSharedPreferences.getInstance(context).setLoginStatus(true);
                listener.goHomeScreen();
                listener.onLoginMessage("Logged in successfully");
            } else {
                listener.onLoginMessage("Account information or password is incorrect");
            }
        }
    }

    private boolean checkLogin(Context context, String email, String password) {
        List<Account> accounts = AppSharedPreferences.getInstance(context).getAccounts();
        if (accounts == null) {
            return false;
        } else {
            for (Account account : accounts) {
                if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
