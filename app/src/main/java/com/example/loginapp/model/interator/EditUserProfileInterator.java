package com.example.loginapp.model.interator;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginapp.data.remote.service.Constant;
import com.example.loginapp.model.entity.UserData;
import com.example.loginapp.model.listener.EditUserProfileListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Objects;

public class EditUserProfileInterator {
    private static final String TAG = "EditUserProfileInterator";

    private final DatabaseReference userRef =
        FirebaseDatabase.getInstance().getReference().child(Constant.USER_REF);

    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    private StorageReference storageRef = storage.getReference();

    private EditUserProfileListener listener;

    public EditUserProfileInterator(EditUserProfileListener listener) {
        this.listener = listener;
    }

    public void getUserData() {
        assert currentUser != null;
        userRef.child(currentUser.getUid()).get()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    UserData user = task.getResult().getValue(UserData.class);
                    listener.getUserData(user);
                }
            });


    }

    public void uploadImageToFirebase(
        @Nullable Uri uri,
        String username,
        String phoneNumber,
        String address
    ) {

        if (username.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            listener.onMessage("Please enter complete information");
            listener.showProcessBar(false);
        } else {
            listener.showProcessBar(true);
            if (uri == null) {
                assert currentUser != null;
                userRef.child(currentUser.getUid()).child(UserData.USERNAME).setValue(username);
                userRef.child(currentUser.getUid()).child(UserData.PHONE_NUMBER).setValue(phoneNumber);
                userRef.child(currentUser.getUid()).child(UserData.ADDRESS).setValue(address);
                listener.showProcessBar(false);
                listener.goUserProfile();
            } else {
                storageRef = storageRef.child(currentUser.getUid());
                UploadTask uploadTask = storageRef.putFile(uri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            userRef.child(currentUser.getUid()).child(UserData.USERNAME)
                                .setValue(username);
                            userRef.child(currentUser.getUid()).child(UserData.PHONE_NUMBER)
                                .setValue(phoneNumber);
                            userRef.child(currentUser.getUid()).child(UserData.ADDRESS)
                                .setValue(address);
                            userRef.child(currentUser.getUid()).child(UserData.PHOTO_URL)
                                .setValue(downloadUri.toString());
                            listener.showProcessBar(false);
                            listener.goUserProfile();
                        } else {
                            listener.showProcessBar(false);
                        }
                    }
                });
            }
        }

    }
}
