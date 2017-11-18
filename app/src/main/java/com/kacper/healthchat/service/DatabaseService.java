package com.kacper.healthchat.service;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacper.healthchat.model.User;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DatabaseService {
    private DatabaseReference mDatabase;


    public void onInit(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void onAuthSuccess(FirebaseUser user, String role) {
        String username = usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail(), role);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email, String role) {
        User user = new User(name, email, role);
        mDatabase.child("users").child(userId).setValue(user);
    }
}
