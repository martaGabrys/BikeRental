package com.app.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.BikeApplication;
import com.app.CashToPayDialog;
import com.app.R;
import com.app.data.BikeRentByUser;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity implements OrderContract.View {

    @BindView(R.id.bike_rented_recycler)
    RecyclerView bikeRentedRecycler;

    @BindView(R.id.bikes_rented_error_image)
    ImageView bikeRentedErrorImage;

    @BindView(R.id.bikes_rented_error_message)
    TextView bikeRentedErrorTextView;

    @OnClick(R.id.button_pay)
    public void onClickButtonPocket() {
        presenter.setTimeOfCanceling();
        presenter.countHowMuchToPay(declaredCash);
    }


    private OrderAdapter adapter;
    private float declaredCash;

    @Inject
    OrderContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        setTitle(sharedPreferences.getString(getString(R.string.MAIL), ""));

        ((BikeApplication) getApplication()).getAppComponent()
                .plus(new OrderModule(this))
                .inject(this);

        declaredCash = Float.valueOf((sharedPreferences.getString(getString(R.string.WALLET), "")));

        adapter = new OrderAdapter();
        bikeRentedRecycler.setLayoutManager(new LinearLayoutManager(this));
        bikeRentedRecycler.setAdapter(adapter);


    }

    @Override
    public void showData(List<BikeRentByUser> bikes) {
        adapter.updateBikes(bikes);
        bikeRentedErrorTextView.setVisibility(View.INVISIBLE);
        bikeRentedErrorImage.setVisibility(View.INVISIBLE);
        bikeRentedRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        bikeRentedRecycler.setVisibility(View.INVISIBLE);
        bikeRentedErrorTextView.setVisibility(View.VISIBLE);
        bikeRentedErrorImage.setVisibility(View.VISIBLE);
    }

    private void openDialogString(String cashToPay) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CashToPayDialog cashToPayDialog = new CashToPayDialog(cashToPay);
        cashToPayDialog.show(fragmentManager, "DIALOG");
    }

    @Override
    public void showDialog(String availableCash) {
        openDialogString(availableCash);
    }

    @Override
    public void changeDeclaredCash(String availableCash) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        sharedPreferences.edit().putString(getString(R.string.WALLET), availableCash).commit();
    }
}
