package com.kacper.healthchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kacper.healthchat.R;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.Message;
import com.kacper.healthchat.presenter.ChatPresenter;
import com.kacper.healthchat.view.adapter.DoctorsAdapter;
import com.kacper.healthchat.view.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 18.11.2017.
 */

public class ChatActivity  extends AppCompatActivity implements ChatView {

    private static final String TAG = "ChatActivity";
    private ChatPresenter chatPresenter = new ChatPresenter(this);
    MessageAdapter messageAdapter;
    List<Message> allMessages = new ArrayList<>();

    @BindView(R.id.currDoctorEmail)
    public TextView currDoctorEmail;
    @BindView(R.id.currDoctorSpeciality)
    public TextView currDoctorSpeciality;
    @BindView(R.id.currDoctorUserName)
    public TextView currDoctorUserName;
    @BindView(R.id.mInputMessage)
    public EditText mInputMessage;
    @BindView(R.id.chatListView)
    public ListView chatListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        ButterKnife.bind(this);
        Doctor doctor = (Doctor)getIntent().getParcelableExtra("currentDoctor");
        chatPresenter.onCreate();
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
        mInputMessage.setText("");
        scrollMyListViewToBottom();
    }

    @Override
    public void displayMessages(final List<Message> messages) {
        messageAdapter = new MessageAdapter(messages, getApplicationContext());
        chatListView.setAdapter(messageAdapter);
        allMessages = messages;
        scrollMyListViewToBottom();
    }

    @Override
    public void updateMessages(List<Message> messages) {
        allMessages = messages;
        chatListView.invalidateViews();
        scrollMyListViewToBottom();
    }

    private void scrollMyListViewToBottom() {
        chatListView.post(new Runnable() {
            @Override
            public void run() {
                chatListView.setSelection(messageAdapter.getCount() - 1);
            }
        });
    }

}
