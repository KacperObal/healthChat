package com.kacper.healthchat.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kacper.healthchat.R;
import com.kacper.healthchat.presenter.DashboardPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements DashboardView {

    DashboardPresenter dashboardPresenter = new DashboardPresenter(this);

    @BindView(R.id.mWelcomeMessage)
    public EditText mWelcomeMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        ButterKnife.bind(this);
        dashboardPresenter.onCreate();
    }


    public void goToDoctorList(View view) {
        Intent k = new Intent(this, DoctorListActivity.class);
        startActivity(k);
    }


    public void goToChat(View view) {
        Intent k = new Intent(this, ChatActivity.class);
        startActivity(k);
    }

    @Override
    public void createWelcomeMessage(String message) {
        mWelcomeMessage.setText(message);
    }

    private void updateWelcomeMessage(){
//        String message = dashboardPresenter.setWelcomeMessage();
//        mWelcomeMessage.setText();
    }
}
