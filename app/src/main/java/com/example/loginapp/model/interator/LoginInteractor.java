package com.example.loginapp.model.interator;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.loginapp.model.listener.LoginListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (!isValidEmail(email) || !isPasswordValid(password)) {
            listener.onLoginMessage("Account information or password is incorrect");
            listener.onShowProcessBar(false);
        } else {
            listener.onShowProcessBar(true);
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.goHomeScreen();
                            listener.onLoginMessage("Logged in successfully");
                            listener.onShowProcessBar(false);
                        } else {
                            listener.onLoginMessage("Account information or password is incorrect");
                            listener.onShowProcessBar(false);
                        }
                    }
                });
        }
    }

    private boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra địa chỉ email
        String emailRegex =
//            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        // Tạo một đối tượng Pattern từ biểu thức chính quy
        Pattern pattern = Pattern.compile(emailRegex);

        // Tạo một đối tượng Matcher từ đối tượng Pattern và đầu vào email
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra xem địa chỉ email có khớp với biểu thức chính quy không
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

}
