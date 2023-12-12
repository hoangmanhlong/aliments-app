package com.example.loginapp.model.interator;

import android.content.Context;
import android.util.Log;

import com.example.loginapp.App;
import com.example.loginapp.data.AppSharedPreferences;
import com.example.loginapp.model.entity.Account;
import com.example.loginapp.model.listener.RegisterListener;
import com.example.loginapp.presenter.HomePresenter;
import com.example.loginapp.presenter.RegisterPresenter;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterInterator {
    private RegisterListener listener;

    public RegisterInterator(RegisterListener listener) {
        this.listener = listener;
    }

    public void register(
        String email,
        String password,
        String confirmPassword
    ) {
        Context context = App.getInstances().getApplicationContext().getApplicationContext();
        if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
            listener.onRegisterMessage("Please enter complete information");
        } else if (!isValidEmail(email)) {
            listener.onRegisterMessage("Email format is wrong, Please re-enter");
        } else if (!isPasswordValid(password)) {
            listener.onRegisterMessage("Password must be more than 6 characters");
        } else if (!password.equals(confirmPassword)) {
            listener.onRegisterMessage("Passwords are not duplicates");
        } else if (!emailIsExists(context, email)) {
            listener.onRegisterMessage("Email already exists");
        } else {
            try {
                AppSharedPreferences.getInstance(context).saveUserAccount(email, password);
                listener.goLoginScreen();
                listener.onRegisterMessage("Sign Up Success");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        // Kiểm tra xem mật khẩu có ít nhất 6 ký tự không
        return password.length() >= 6;
    }

    private boolean emailIsExists(Context context, String email) {
        List<Account> accounts = AppSharedPreferences.getInstance(context).getAccounts();
        if (accounts == null)
            return true;
        else
            for (Account account : accounts)
                if (Objects.equals(account.getEmail(), email))
                    return false;
        return true;
    }
}
