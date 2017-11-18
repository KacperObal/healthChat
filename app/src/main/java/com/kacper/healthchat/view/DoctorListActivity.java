package com.kacper.healthchat.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kacper.healthchat.R;
import com.kacper.healthchat.presenter.DoctorListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DoctorListActivity  extends AppCompatActivity implements DoctorListView {

    DoctorListPresenter doctorListPresenter = new DoctorListPresenter(this);

    @BindView(R.id.mDoctorListTitleText)
    public EditText mEmailField;
    @BindView(R.id.mDoctorList)
    public EditText mPasswordField;

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
    public void onSelectedDoctor() {

    }
}
