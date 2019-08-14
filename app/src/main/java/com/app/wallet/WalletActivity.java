package com.app.wallet;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.BikeApplication;
import com.app.R;
import com.app.bike.BikeActivity;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends AppCompatActivity implements WalletContract.View {


    @BindView(R.id.wallet_welcome_msg)
    TextView walletMessage;

    @BindView(R.id.wallet_cash)
    TextInputLayout walletCashAmount;


    @OnClick(R.id.wallet_button)
    public void onButtonClick() {
        presenter.onDeclareButtonClick((walletCashAmount.getEditText()).getText().toString());
    }

    @Inject
    WalletContract.Presenter presenter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        setTitle(sharedPreferences.getString(getString(R.string.MAIL), ""));

        ((BikeApplication) getApplication()).getAppComponent()
                .plus(new WalletModule(this))
                .inject(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        sharedPreferences.edit().putString(getString(R.string.WALLET), walletCashAmount.getEditText().getText().toString()).commit();

    }

    @Override
    public void showWalletError(String walletError) {
        walletCashAmount.setError(walletError);
    }

    @Override
    public void declareCashSuccessfully() {
        Toast.makeText(this, R.string.declare_success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BikeActivity.class);
        startActivity(intent);
    }

    @Override
    public void clearWalletError() {
        walletCashAmount.setError(null);
    }

}
