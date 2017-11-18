package com.kacper.healthchat.presenter;

import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.view.DoctorListView;
import com.kacper.healthchat.view.LoginView;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DoctorListPresenter implements Presenter {

    private static final String TAG = "DoctorListPresenter";
    public DoctorListView view;

    public DoctorListPresenter(DoctorListView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
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

        view.displayDoctorList(doctorArray);
    }
}
