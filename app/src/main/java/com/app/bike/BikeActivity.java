package com.app.bike;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.BikeApplication;
import com.app.FilterTypeDialog;
import com.app.R;
import com.app.data.Bike;
import com.app.order.OrderActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BikeActivity extends AppCompatActivity implements BikeContract.View, AdapterView.OnItemSelectedListener, FilterTypeDialog.FilterTypeDialogListener {

    @BindView(R.id.spinner)
    Spinner filterSpinner;

    @BindView(R.id.bike_recycler)
    RecyclerView bikeRecycler;

    @BindView(R.id.bike_progress)
    ProgressBar bikeProgress;

    @BindView(R.id.bikes_error_image)
    ImageView bikeErrorImage;

    @BindView(R.id.bikes_error_message)
    TextView bikeErrorTextView;

    @OnClick(R.id.button_pocket)
    public void onClickButtonPocket() {
        presenter.updateRentedBikeTable(adapter.bikeId, adapter.time);
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private BikeAdapter adapter;
    private float declaredCash;

    @Inject
    BikeContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FILE_PREF_NAME), MODE_PRIVATE);
        setTitle(sharedPreferences.getString(getString(R.string.MAIL), ""));

        ((BikeApplication) getApplication()).getAppComponent()
                .plus(new BikeModule(this))
                .inject(this);

        declaredCash = Float.valueOf((sharedPreferences.getString(getString(R.string.WALLET), "")));

        setSpinner();

        adapter = new BikeAdapter();
        bikeRecycler.setLayoutManager(new LinearLayoutManager(this));
        bikeRecycler.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateBikeTable(adapter.bikeId);
    }

    private void setSpinner() {
        filterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.filter_type, R.layout.simple_spinner_item_mg);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapterSpinner);
    }

    @Override
    public void showProgress() {
        presenter.updateBikeTable(adapter.bikeId);
        bikeRecycler.setVisibility(View.INVISIBLE);
        bikeErrorTextView.setVisibility(View.INVISIBLE);
        bikeErrorImage.setVisibility(View.INVISIBLE);
        bikeProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<Bike> bikes) {
        adapter.updateBikes(bikes);
        bikeProgress.setVisibility(View.INVISIBLE);
        bikeErrorTextView.setVisibility(View.INVISIBLE);
        bikeErrorImage.setVisibility(View.INVISIBLE);
        bikeRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        bikeRecycler.setVisibility(View.INVISIBLE);
        bikeProgress.setVisibility(View.INVISIBLE);
        bikeErrorTextView.setVisibility(View.VISIBLE);
        bikeErrorImage.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        switch (item) {
            case "BRAND":
                openDialogString("PICK A BRAND", presenter.getFilterStringArrayBrand());
                break;
            case "COLOR":
                openDialogString("PICK A COLOR", presenter.getFilterStringArrayColor());
                break;
            case "AVAILABILITY":
                boolean[] status = presenter.getFilterStringArrayStatus();
                String[] statusString = new String[status.length];

                for (int i = 0; i < status.length; i++) {
                    statusString[i] = String.valueOf(status[i]);
                }
                openDialogString("IS FREE TO RENT ?", statusString);
                break;

        }
    }


    private void openDialogString(String item, String[] filterTypesArray) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FilterTypeDialog filterTypeDialog = new FilterTypeDialog(item, filterTypesArray);
        filterTypeDialog.show(fragmentManager, "DIALOG");
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public float getDeclaredCashAmount() {
        return declaredCash;
    }

    @Override
    public void onDialogItemClick(DialogFragment dialog, String title, String item) {
        switch (title) {
            case "PICK A BRAND":
                presenter.getBikesFilterByBrand(item);
                break;

            case "PICK A COLOR":
                presenter.getBikesFilterByColor(item);
                break;

            case "IS FREE TO RENT ?":
                presenter.getBikesFilterByStatus(Boolean.valueOf(item));
                break;
        }
        dialog.onDestroyView();
    }


}


