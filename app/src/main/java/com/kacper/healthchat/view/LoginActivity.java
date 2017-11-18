package com.kacper.healthchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.kacper.healthchat.R;
import com.kacper.healthchat.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @BindView(R.id.mEmailField)
    public EditText mEmailField;
    @BindView(R.id.mPasswordField)
    public EditText mPasswordField;
    @BindView(R.id.mImADoctorCheckbox)
    public CheckBox mImADoctorCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        loginPresenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.onStop();
    }


    public void onRegistrationButtonClick(View view){
        loginPresenter.createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mImADoctorCheckbox.isChecked(), LoginActivity.this);
    }

    public void onLoginButtonClick(View view){
        loginPresenter.signIn(mEmailField.getText().toString(), mPasswordField.getText().toString(), mImADoctorCheckbox.isChecked(),LoginActivity.this);
    }

    @Override
    public void onAuthFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        loginPresenter.goToDashboard();
    }

    @Override
    public void showFailMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToDashboard() {
        Intent k = new Intent(this, DashboardActivity.class);
        startActivity(k);
    }
}
