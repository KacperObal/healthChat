package com.kacper.healthchat.presenter;

import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.service.DatabaseService;
import com.kacper.healthchat.view.DoctorListView;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DoctorListPresenter implements Presenter {

    private static final String TAG = "DoctorListPresenter";
    public DoctorListView view;
    public DatabaseService databaseService = new DatabaseService();


    public DoctorListPresenter(DoctorListView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        databaseService.onInit();
        getDoctorList();
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

    public void getDoctorList(){

        Scheduler ioScheduler = Schedulers.io();
        Scheduler uiScheduler = AndroidSchedulers.mainThread();

        databaseService.loadDoctor()
                .subscribeOn(ioScheduler)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        view.showLoadingDoctors();
                    }
                })
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Doctor>>() {
                    @Override
                    public void onSuccess(List<Doctor> doctorList) {
                        if (!doctorList.isEmpty()) {
                            view.displayDoctorsList(doctorList);
                        } else {
                            view.showNoDoctorAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showNoDoctorAvailable();
                    }
                });
    }
}
