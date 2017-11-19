package com.kacper.healthchat.presenter;

import android.util.Log;

import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.Message;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.service.AuthService;
import com.kacper.healthchat.service.DatabaseService;
import com.kacper.healthchat.view.ChatView;
import com.kacper.healthchat.view.DoctorListView;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kacper on 19.11.2017.
 */

public class ChatPresenter implements Presenter {

    private static final String TAG = "ChatActivity";

    public Doctor doctor;
    public User currUser;
    public ChatView view;

    AuthService authService = new AuthService();
    DatabaseService databaseService = new DatabaseService();


    public ChatPresenter(ChatView view) {
        this.view = view;
    }

    public void onInit(Doctor doctor){
        this.doctor = doctor;
    }

    @Override
    public void onCreate() {

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
    public void getCurrentUser(){
        Scheduler ioScheduler = Schedulers.io();
        Scheduler uiScheduler = AndroidSchedulers.mainThread();

        authService.getCurrentUser()
                .subscribeOn(ioScheduler)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //view.showLoadingDoctors();
                    }
                })
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) {
                            currUser = user;
                        } else {
                            //view.showNoDoctorAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //view.showNoDoctorAvailable();
                    }
                });
    }


    public void onSendMessage(String inputMessage) {
        Message message = new Message(inputMessage,currUser.getId(),doctor.getId());
    }
}
