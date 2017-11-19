package com.kacper.healthchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.kacper.healthchat.R;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.presenter.DoctorListPresenter;
import com.kacper.healthchat.view.adapter.DoctorsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DoctorListActivity  extends AppCompatActivity implements DoctorListView {

    DoctorListPresenter doctorListPresenter = new DoctorListPresenter(this);

    @BindView(R.id.mDoctorList)
    public ListView mDoctorListView;
    ArrayAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_list_activity);
        ButterKnife.bind(this);
        doctorListPresenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSelectedDoctor(Doctor doctor) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("currentDoctor", doctor);
        startActivity(intent);
    }

    @Override
    public void displayDoctorsList(final List<Doctor> doctorList) {
        DoctorsAdapter doctorsAdapter;
        doctorsAdapter = new DoctorsAdapter(doctorList, getApplicationContext());
        mDoctorListView.setAdapter(doctorsAdapter);
        mDoctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctor doctor = doctorList.get(position);
                onSelectedDoctor(doctor);
            }
        });
    }

    @Override
    public void showLoadingDoctors() {

    }

    @Override
    public void showNoDoctorAvailable() {

    }
}
