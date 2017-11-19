package com.kacper.healthchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kacper.healthchat.R;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.presenter.ChatPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 18.11.2017.
 */

public class ChatActivity  extends AppCompatActivity implements ChatView {

    private static final String TAG = "ChatActivity";
    private ChatPresenter chatPresenter = new ChatPresenter(this);
    @BindView(R.id.currDoctorEmail)
    public TextView currDoctorEmail;
    @BindView(R.id.currDoctorSpeciality)
    public TextView currDoctorSpeciality;
    @BindView(R.id.currDoctorUserName)
    public TextView currDoctorUserName;
    @BindView(R.id.mInputMessage)
    public EditText mInputMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        ButterKnife.bind(this);
        Doctor doctor = (Doctor)getIntent().getParcelableExtra("currentDoctor");
        chatPresenter.onInit(doctor);
        currDoctorEmail.setText(doctor.getEmail());
        currDoctorSpeciality.setText(doctor.getSpeciality());
        currDoctorUserName.setText(doctor.getUsername());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onSendMessage(View view){

        chatPresenter.onSendMessage(mInputMessage.getText().toString());
    }

}
