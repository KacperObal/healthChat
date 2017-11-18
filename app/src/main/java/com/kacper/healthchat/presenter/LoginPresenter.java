package com.kacper.healthchat.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.view.LoginView;

/**
 * Created by Kacper on 18.11.2017.
 */

public class LoginPresenter implements Presenter{

    private static final String TAG = "LoginActivity";
    private final LoginView view;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount(String email, String password, Boolean isDoctor, Activity activity) {
        Log.d(TAG, "createAccount:" + email);
        final String role = isDoctor ? "doctor" : "patient";

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            onAuthSuccess(user, role);



                            view.onAuthSuccess("Registration Success");
                        } else {
                            view.signInFail(task.getException().getMessage());
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        Log.w(TAG, "test");
    }

    public void signIn(String email, String password, Boolean isDoctor, Activity activity) {
        Log.d(TAG, "signIn:" + email);
        final String role = isDoctor ? "doctor" : "patient";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            view.onAuthSuccess("Login Success");
                        } else {
                            view.signInFail(task.getException().getMessage());
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void goToDashboard(){
        Log.d(TAG, "Going to dashboard");
        view.goToDashboard();
    }

    private void onAuthSuccess(FirebaseUser user, String role) {
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
