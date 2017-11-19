package com.kacper.healthchat.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    @BindView(R.id.mSpecialityField)
    public EditText mSpecialityField;
    @BindView(R.id.mImADoctorCheckbox)
    public CheckBox mImADoctorCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        loginPresenter.onCreate();
        onRoleChanged();
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

    public void onRoleChanged() {
        mImADoctorCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mSpecialityField.setVisibility(View.VISIBLE);
                } else {
                    mSpecialityField.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    public void onRegistrationButtonClick(View view) {
        loginPresenter.createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(),
                mImADoctorCheckbox.isChecked(), mSpecialityField.getText().toString(), LoginActivity.this);
    }

    public void onLoginButtonClick(View view) {
        loginPresenter.signIn(mEmailField.getText().toString(), mPasswordField.getText().toString(),
                mImADoctorCheckbox.isChecked(), LoginActivity.this);
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
