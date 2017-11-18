package com.kacper.healthchat.view;

import android.view.View;

/**
 * Created by Kacper on 18.11.2017.
 */

public interface LoginView  {
    void onRegistrationButtonClick(View view);
    void onLoginButtonClick(View view);
    void onAuthFail(String message);
    void onAuthSuccess(String messageSuccess);
    void showFailMessage(String message);
    void goToDashboard();
}
