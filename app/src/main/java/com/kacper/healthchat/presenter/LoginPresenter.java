package com.kacper.healthchat.presenter;

import android.app.Activity;
import android.util.Log;

import com.kacper.healthchat.service.AuthService;
import com.kacper.healthchat.utli.OperationInfo;
import com.kacper.healthchat.view.LoginView;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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

    public void createAccount(String email, String password, Boolean isDoctor, String speciality, Activity activity) {

        Scheduler ioScheduler = Schedulers.io();
        Scheduler uiScheduler = AndroidSchedulers.mainThread();

        authService
                .createAccount(email, password, isDoctor, speciality, activity)
                .subscribeOn(ioScheduler)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showFailMessage("Creating account in progres");
                    }
                })
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableSingleObserver<OperationInfo>() {
                    @Override
                    public void onSuccess(OperationInfo operationInfo) {
                        if (operationInfo.getSuccess()) {
                            view.onAuthSuccess(operationInfo.getMessage());
                        } else {
                            view.onAuthFail(operationInfo.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showFailMessage(e.getMessage());
                    }
                });
    }

    public void signIn(String email, String password, Boolean isDoctor, Activity activity) {
        Scheduler ioScheduler = Schedulers.io();
        Scheduler uiScheduler = AndroidSchedulers.mainThread();

        authService
                .signIn(email, password, isDoctor, activity)
                .subscribeOn(ioScheduler)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showFailMessage("Loging in progres");
                    }
                })
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableSingleObserver<OperationInfo>() {
                    @Override
                    public void onSuccess(OperationInfo operationInfo) {
                        if (operationInfo.getSuccess()) {
                            view.onAuthSuccess(operationInfo.getMessage());
                        } else {
                            view.onAuthFail(operationInfo.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showFailMessage(e.getMessage());
                    }
                });
    }

    public void goToDashboard() {
        Log.d(TAG, "Going to dashboard");
        view.goToDashboard();
    }

}
