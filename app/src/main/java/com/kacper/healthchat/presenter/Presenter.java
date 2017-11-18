package com.kacper.healthchat.presenter;

/**
 * Created by Kacper on 18.11.2017.
 */

public interface Presenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
    void onStart();
    void onStop();
}
