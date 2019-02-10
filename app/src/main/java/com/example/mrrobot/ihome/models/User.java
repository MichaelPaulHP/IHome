package com.example.mrrobot.ihome.models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private static User instance;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private User() {
        mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    public void signOut() {
        // Firebase sign out
        mAuth.signOut();
    }
    public String getName(){
        return currentUser.getDisplayName();
    }
    public Uri getPhotoUrl(){
        return currentUser.getPhotoUrl();
    }
    public String getId(){
        return currentUser.getUid();
    }
}
