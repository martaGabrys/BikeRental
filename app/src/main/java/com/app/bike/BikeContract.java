package com.app.bike;

import com.app.data.Bike;

import java.util.List;

public interface BikeContract {

    interface View {

        void showProgress();

        void showData(List<Bike> bikes);

        void showError();

        float getDeclaredCashAmount();
    }

    interface Presenter {

        void getBikes();

        String[] getFilterStringArrayColor();

        String[] getFilterStringArrayBrand();

        boolean [] getFilterStringArrayStatus();

        void getBikesFilterByBrand(String item);

        void getBikesFilterByColor(String item);

        void getBikesFilterByStatus(boolean item);

        void updateBikeTable(List<Integer> bikeId);

        void updateRentedBikeTable(List<Integer> bikeId, List<String> time);
    }
}
