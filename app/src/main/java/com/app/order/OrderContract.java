package com.app.order;

import com.app.data.BikeRentByUser;

import java.util.List;

public interface OrderContract {

    interface View {

        void showData(List<BikeRentByUser> bikeRentByUserList);

        void showError();

        void showDialog(String availableCash);

        void changeDeclaredCash(String availableCash);

    }

    interface Presenter {

        void getRentedBikes();

        void setTimeOfCanceling();

        void countHowMuchToPay(float declaredCash);
    }

}