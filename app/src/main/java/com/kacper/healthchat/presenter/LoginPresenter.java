package com.kacper.healthchat.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.service.AuthService;
import com.kacper.healthchat.utli.OperationInfo;
import com.kacper.healthchat.view.LoginView;

import io.reactivex.Observable;

/**
 * Created by Kacper on 18.11.2017.
 */

public class LoginPresenter implements Presenter {

    private static final String TAG = "LoginActivity";
    private final LoginView view;
    private AuthService authService = new AuthService();

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        authService.initAuth();
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
        authService.onStartAuth();
    }

    @Override
    public void onStop() {
        authService.onStopAuth();
    }

    public void createAccount(String email, String password, Boolean isDoctor, Activity activity) {
        OperationInfo operationInfo = authService.createAccount(email, password, isDoctor, activity);
        if (operationInfo.getSuccess()) {
            view.onAuthSuccess(operationInfo.getMessage());
        } else {
            view.onAuthFail(operationInfo.getMessage());
        }
    }

    public void signIn(String email, String password, Boolean isDoctor, Activity activity) {
        OperationInfo operationInfo = authService.signIn(email, password, isDoctor, activity);
        if (operationInfo.getSuccess()) {
            view.onAuthSuccess(operationInfo.getMessage());
        } else {
            view.onAuthFail(operationInfo.getMessage());
        }
    }

    public void goToDashboard() {
        Log.d(TAG, "Going to dashboard");
        view.goToDashboard();
    }

}
