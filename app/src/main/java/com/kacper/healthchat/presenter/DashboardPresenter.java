package com.kacper.healthchat.presenter;

import com.kacper.healthchat.view.DashboardView;
import com.kacper.healthchat.view.LoginView;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DashboardPresenter implements Presenter {

    private static final String TAG = "DashboardActivity";
    private final DashboardView view;

    public DashboardPresenter(DashboardView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        createWelcomeMessage();
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

    }

    @Override
    public void onStop() {

    }

    private void createWelcomeMessage(){
        //Todo : get user data
        String message = "siemanko";
        view.createWelcomeMessage(message);
    }

}
