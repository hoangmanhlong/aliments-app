package com.example.loginapp.view.fragment.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.loginapp.R;
import com.example.loginapp.databinding.FragmentLoginBinding;
import com.example.loginapp.presenter.LoginPresenter;
import com.example.loginapp.view.activities.MainActivity;
import com.example.loginapp.view.state.LoginButtonObserver;

import java.util.Objects;

public class LoginFragment extends Fragment implements LoginView {
    private FragmentLoginBinding binding;
    private LoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLoginFragment(this);
        hideKeyboardFrom(this.requireActivity(), view);
        loginPresenter = new LoginPresenter(this);

        if (getArguments() != null) {
            String email = getArguments().getString("email");
            String password = getArguments().getString("password");
            if (!email.isEmpty() && !password.isEmpty()) {
                binding.emailInput.setText(email);
                binding.passwordInput.setText(password);
                binding.loginEmailBtn.setEnabled(true);
                onEmailClick();
            }
        }

        if(Objects.requireNonNull(binding.emailInput.getText()).toString().isEmpty() || Objects.requireNonNull(
            binding.passwordInput.getText()).toString().isEmpty()) {
            binding.loginEmailBtn.setEnabled(false);
        } else {
            binding.loginEmailBtn.setEnabled(true);
        }

        view.setOnTouchListener((v, event) -> {
            hideKeyboardFrom(requireContext(), view);
            return false;
        });

        binding.emailInput.addTextChangedListener(new LoginButtonObserver(
            binding.loginEmailBtn,
            binding.emailInput,
            binding.passwordInput
        ));

        binding.passwordInput.addTextChangedListener(new LoginButtonObserver(
            binding.loginEmailBtn,
            binding.emailInput,
            binding.passwordInput
        ));
    }

    public void onNumberPhoneBtn() {
        binding.numberPhoneInput.setVisibility(View.VISIBLE);
        binding.linearInputEmail.setVisibility(View.GONE);
        binding.loginPhoneNumber.setBackgroundResource(R.drawable.background_login_select);
        binding.loginEmail.setBackgroundResource(R.drawable.wrap_select_option_login_background);
    }

    public void onEmailClick() {
        binding.numberPhoneInput.setVisibility(View.GONE);
        binding.linearInputEmail.setVisibility(View.VISIBLE);
        binding.loginPhoneNumber.setBackgroundResource(R.drawable.wrap_select_option_login_background);
        binding.loginEmail.setBackgroundResource(R.drawable.background_login_select);
    }

    public void onLogin() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        loginPresenter.login(email, password, requireActivity());
    }

    public void goRegisterScreen() {
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_loginFragment_to_registerFragment);
    }

    public void goHomeScreen() {
        startActivity(new Intent(requireContext(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onLoginMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowProcessBar(Boolean show) {
        if (show) {
            binding.loginEmailBtn.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginEmailBtn.setVisibility(View.VISIBLE);
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
