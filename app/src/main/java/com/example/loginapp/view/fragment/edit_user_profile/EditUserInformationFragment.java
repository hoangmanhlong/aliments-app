package com.example.loginapp.view.fragment.edit_user_profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.loginapp.R;
import com.example.loginapp.databinding.FragmentEditUserInformationBinding;
import com.example.loginapp.model.MyOpenDocumentContract;
import com.example.loginapp.model.entity.UserData;
import com.example.loginapp.presenter.EditUserProfilePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class EditUserInformationFragment extends Fragment implements EditUserProfileView {

    private FragmentEditUserInformationBinding binding;

    private EditUserProfilePresenter presenter;

    private Uri photoUrl = null;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentEditUserInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new EditUserProfilePresenter(this);
        binding.setFragment(this);
        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
    }

    private ActivityResultLauncher<String[]> openDocument = registerForActivityResult(
        new MyOpenDocumentContract(),
        new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {
                    binding.userImage.setImageURI(uri);
                    photoUrl = uri;
                } else {
                    onMessage("No file selected");
                }

            }
        }
    );

    public void onEditImage() {
        openDocument.launch(new String[]{"image/*"});
    }

    @Override
    public void loadUserData(UserData userData) {
        String username = userData.getUsername();
        String phoneNumber = userData.getPhoneNumber();
        String address = userData.getAddress();
        String photoUrl = userData.getPhotoUrl();
        binding.setUserData(new UserData(username, phoneNumber, address, photoUrl));
    }

    public void saveUserData() {
        String username = binding.usernameInput.getText().toString().trim();
        String phoneNumber = binding.phoneNumberInput.getText().toString().trim();
        String address = binding.addressInput.getText().toString().trim();
        if (username.isEmpty()) username = "";
        if (phoneNumber.isEmpty()) phoneNumber = "";
        if (address.isEmpty()) address = "";
        presenter.saveUserData(
            photoUrl,
            username,
            phoneNumber,
            address
        );
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProcessBar(Boolean show) {
        if (show) {
            binding.registerProcessBar.setVisibility(View.VISIBLE);
            binding.save.setVisibility(View.GONE);
        } else {
            binding.registerProcessBar.setVisibility(View.GONE);
            binding.save.setVisibility(View.VISIBLE);
        }
    }

    public void onNavigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    @Override
    public void goUserProfile() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}