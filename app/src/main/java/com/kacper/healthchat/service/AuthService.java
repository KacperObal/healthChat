package com.kacper.healthchat.service;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kacper.healthchat.utli.OperationInfo;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Kacper on 18.11.2017.
 */

public class AuthService {


    private static final String TAG = "AuthService";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseService databaseService = new DatabaseService();

    public void initAuth() {
        mAuth = FirebaseAuth.getInstance();
        databaseService.onInit();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public OperationInfo createAccount(String email, String password, Boolean isDoctor, Activity activity) {
        Log.d(TAG, "createAccount:" + email);
        final String role = isDoctor ? "doctor" : "patient";
        final OperationInfo operationInfo = new OperationInfo();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            operationInfo.setMessage("Registration Success");
                            operationInfo.setSuccess(Boolean.TRUE);
                            databaseService.onAuthSuccess(user, role);
                        } else {
                            operationInfo.setMessage(task.getException().getMessage());
                            operationInfo.setSuccess(Boolean.FALSE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        Log.w(TAG, "test");
        return operationInfo;
    }

    public OperationInfo signIn(String email, String password, Boolean isDoctor, Activity activity) {
        Log.d(TAG, "signIn:" + email);
        final String role = isDoctor ? "doctor" : "patient";
        final OperationInfo operationInfo = new OperationInfo();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            operationInfo.setMessage("Login Success");
                            operationInfo.setSuccess(Boolean.TRUE);
                        } else {
                            operationInfo.setMessage(task.getException().getMessage());
                            operationInfo.setSuccess(Boolean.FALSE);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
        return operationInfo;
    }

    public void onStopAuth(){
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onStartAuth(){
        mAuth.addAuthStateListener(mAuthListener);
    }
}
