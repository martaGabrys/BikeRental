package com.app.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.app.BikeApplication;
import com.app.R;
import com.app.wallet.WalletActivity;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {


    @BindView(R.id.login_email)
    TextInputLayout loginEmail;

    @BindView(R.id.login_password)
    TextInputLayout loginPassword;

    @OnClick(R.id.login_button)
    public void onButtonClick() {
        presenter.onLoginButtonClick(
                loginEmail.getEditText().getText().toString(),
                loginPassword.getEditText().getText().toString()
        );
    }

    @Inject
    LoginContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        ((BikeApplication) getApplication()).getAppComponent()
                .plus(new LoginModule(this))
                .inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        sharedPreferences.edit().putString(getString(R.string.MAIL), loginEmail.getEditText().getText().toString()).apply();
    }


    @Override
    public void showEmailError(int errorMessageId) {
        loginEmail.setError(getString(errorMessageId));
    }

    @Override
    public void showPasswordError(int errorMessageId) {
        loginPassword.setError(getString(errorMessageId));
    }

    @Override
    public void logIn() {
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void clearErrors() {
        loginEmail.setError(null);
        loginPassword.setError(null);
    }


}
