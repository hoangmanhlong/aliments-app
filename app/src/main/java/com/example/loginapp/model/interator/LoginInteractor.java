package com.example.loginapp.model.interator;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.loginapp.model.listener.LoginListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor {

    private FirebaseAuth mAuth;


    private LoginListener listener;

    public LoginInteractor(LoginListener listener) {
        this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(
        String email,
        String password,
        Activity activity
    ) {
        if (email.equals("") || password.equals("")) {
            listener.onLoginMessage("Please enter complete information");
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.goHomeScreen();
                            listener.onLoginMessage("Logged in successfully");
                        } else {
                            listener.onLoginMessage("Account information or password is incorrect");
                        }
                    }
                });
        }
    }
}
