package com.example.loginapp.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class InternetCheck {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            // Check if there is an active network connection
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true; // Internet is available
            }
        }

        return false; // No active network connection
    }
}

