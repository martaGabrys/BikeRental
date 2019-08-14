package com.app.bike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.data.Bike;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.ViewHolder> {

    private List<Bike> bikes = new ArrayList<>();
    private int selectedItemPosition = -1;
    public List<Integer> bikeId = new ArrayList<>();
    public List<String> time = new ArrayList<>();

    public void updateBikes(List<Bike> bikesList) {
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
                        .inflate(R.layout.item_bike, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setup(bikes.get(position));

        if (bikes.get(position).isFreeToRent() == false) {
            holder.bikeAvailable.setText(R.string.bikes_not_available);
            holder.checkBox.setVisibility(INVISIBLE);

        } else {
            holder.bikeAvailable.setText(R.string.bikes_available);
            holder.checkBox.setVisibility(VISIBLE);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setTag(position);
                Integer pos = (Integer) holder.checkBox.getTag();

                if (bikes.get(pos).isFreeToRent()) {
                    holder.bikeAvailable.setText(R.string.bikes_not_available);
                    holder.checkBox.setVisibility(INVISIBLE);
                    bikeId.add(bikes.get(pos).id);
                    time.add(DateFormat.getTimeInstance().format(new Date()));
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_bike_card_view)
        CardView itemBikeCardView;

        @BindView(R.id.item_bike_brand)
        TextView bikeBrand;

        @BindView(R.id.item_bike_color)
        TextView bikeColor;

        @BindView(R.id.image_bike_item)
        ImageView bikeImage;

        @BindView(R.id.item_bike_price)
        TextView bikePrice;

        @BindView(R.id.item_bike_available)
        TextView bikeAvailable;

        @BindView(R.id.checkbox)
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setup(Bike bike) {

            bikeBrand.setText(bike.brand.toUpperCase());
            bikePrice.setText(String.valueOf(bike.getPricePerDayRent()));
            bikeColor.setText(bike.color.toUpperCase());

        }
    }
}
