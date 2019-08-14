package com.app.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.data.BikeRentByUser;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<BikeRentByUser> bikes = new ArrayList<>();

    public void updateBikes(List<BikeRentByUser> bikesList) {
        bikes.clear();
        bikes.addAll(bikesList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_rented_bike, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setup(bikes.get(position));
    }


    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_bike_card_view)
        CardView itemBikeCardView;

        @BindView(R.id.item_rented_bike_brand)
        TextView bikeBrand;

        @BindView(R.id.image_rented_bike_item)
        ImageView bikeImage;

        @BindView(R.id.item_rented_bike_price)
        TextView price;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setup(BikeRentByUser bike) {
            bikeBrand.setText(bike.brand);
            price.setText(String.valueOf(bike.pricePerDayRent));
        }
    }
}
