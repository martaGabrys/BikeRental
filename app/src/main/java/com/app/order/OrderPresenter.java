package com.app.order;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.app.BikeApplication;
import com.app.dao.BikeDao;
import com.app.data.BikeRentByUser;
import com.app.database.AppDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OrderPresenter implements OrderContract.Presenter, LifecycleObserver {

    private OrderContract.View view;
    private BikeDao bikeDao;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public OrderPresenter(OrderContract.View view, BikeDao bikeDao) {
        this.view = view;
        ((LifecycleOwner) this.view).getLifecycle().addObserver(this);
        this.bikeDao = bikeDao;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart() {
        getRentedBikes();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void getRentedBikes() {
        compositeDisposable.add(BikeApplication.getDb().bikeDao().getAllRentedBikes()
                .subscribeOn(Schedulers.io())
                .flatMapObservable(bikes -> {
                    return Observable.just(bikes);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bikes -> {
                            view.showData(bikes);
                        },
                        throwable -> view.showError())
        );
    }

    @Override
    public void setTimeOfCanceling() {

        String timeOfCanceling = DateFormat.getTimeInstance().format(new Date());
        AppDatabase db = BikeApplication.getDb();
        db.bikeDao().setTimeOfCanceling(timeOfCanceling);

    }

    @Override
    public void countHowMuchToPay(float declaredCash) {

        AppDatabase db = BikeApplication.getDb();

        float toPay = 0;
        List<BikeRentByUser> rentedBikeList;
        rentedBikeList = db.bikeDao().getRentedBikeList();

        for (int i = 0; i < rentedBikeList.size(); i++) {
            float price = db.bikeDao().getPriceDay(String.valueOf(rentedBikeList.get(i).bikeId));
            String timeStart = rentedBikeList.get(i).timeOfRenting;
            String timeEnd = rentedBikeList.get(i).timeOfCanceling;

            float priceToRent = price * (timeConvertToInt(timeEnd) - timeConvertToInt(timeStart));
            toPay = toPay + priceToRent;
        }

        isRequiredToPay(declaredCash - toPay);
    }

    private int timeConvertToInt(String item) {

        int timeSek;

        if (item.length() == 10) {
            String item2 = item.substring(0, 7);
            String item3 = item2.substring(0, 1);
            String item4 = item2.substring(2, 4);
            String item5 = item2.substring(5);
            timeSek = (Integer.valueOf(item3) + 12) * 3600 + Integer.valueOf(item4) * 60 + Integer.valueOf(item5);
        } else {
            String item2 = item.substring(0, 8);
            String item3 = item2.substring(0, 2);
            String item4 = item2.substring(3, 5);
            String item5 = item2.substring(6);
            timeSek = Integer.valueOf(item3) * 3600 + Integer.valueOf(item4) * 60 + Integer.valueOf(item5);
        }

        return timeSek;
    }

    private void isRequiredToPay(float availableCash) {

        if (availableCash <= 0) {
            view.showDialog(String.valueOf(availableCash));
        } else {
            view.changeDeclaredCash(String.valueOf(availableCash));
        }

    }

}
