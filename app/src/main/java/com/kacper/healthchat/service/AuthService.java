package com.kacper.healthchat.service;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.utli.OperationInfo;
import com.kacper.healthchat.utli.ResultCallback;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

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

    public Single<OperationInfo> createAccount(final String email, final String password, Boolean isDoctor, final String speciality, final Activity activity) {
        Log.d(TAG, "createAccount:" + email);
        final String role = isDoctor ? "doctor" : "patient";

        return Single.create(new SingleOnSubscribe<OperationInfo>() {

            @Override
            public void subscribe(final SingleEmitter<OperationInfo> emitter) throws Exception {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    databaseService.onAuthSuccess( user, role, speciality);  //TODO to tez powinno bys asynchroniczne

                                    emitter.onSuccess(new OperationInfo("Registration success", Boolean.TRUE));
                                } else {
                                    emitter.tryOnError(new FirebaseException(task.getException().getMessage()));
                                }
                            }
                        });
            }
        });
    }

    public Single<OperationInfo> signIn(final String email, final String password, Boolean isDoctor, final Activity activity) {
        Log.d(TAG, "signIn:" + email);
        final String role = isDoctor ? "doctor" : "patient";

        return Single.create(new SingleOnSubscribe<OperationInfo>() {

            @Override
            public void subscribe(final SingleEmitter<OperationInfo> emitter) throws Exception {
                Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    emitter.onSuccess(new OperationInfo("Login success: " + mAuth.getCurrentUser(), Boolean.TRUE));
                                } else {
                                    emitter.tryOnError(new FirebaseException(task.getException().getMessage()));
                                }
                            }
                        });
            }
        });
    }

    public void onStopAuth() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public Single<User> getCurrentUser(){
        final String id = mAuth.getCurrentUser().getUid();
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emiter) throws Exception {
                databaseService.getCurrentUserFromDB(id, new ResultCallback<User>() {
                    @Override
                    public void onSuccess(@NonNull User result) {
                        emiter.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        emiter.tryOnError(exception);
                    }
                });
            }
        });
    }

    public void onStartAuth() {
        mAuth.addAuthStateListener(mAuthListener);
    }
}
