package com.app.bike;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.app.BikeApplication;

import com.app.dao.BikeDao;
import com.app.data.BikeRentByUser;
import com.app.database.AppDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BikePresenter implements BikeContract.Presenter, LifecycleObserver {

    private BikeContract.View view;
    private BikeDao bikeDao;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BikePresenter(BikeContract.View view, BikeDao bikeDao) {
        this.view = view;
        ((LifecycleOwner) this.view).getLifecycle().addObserver(this);
        this.bikeDao = bikeDao;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart() {
        getBikes();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void getBikes() {
        view.showProgress();
        compositeDisposable.add(BikeApplication.getDb().bikeDao().getBikeByPrice(view.getDeclaredCashAmount())
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
    public String[] getFilterStringArrayColor() {
        return BikeApplication.getDb().bikeDao().getAllColor();
    }

    @Override
    public String[] getFilterStringArrayBrand() {
        return BikeApplication.getDb().bikeDao().getAllBrand();
    }

    @Override
    public boolean[] getFilterStringArrayStatus() {
        return BikeApplication.getDb().bikeDao().getAllStatus();
    }

    @Override
    public void getBikesFilterByBrand(String item) {
        view.showProgress();
        compositeDisposable.add(BikeApplication.getDb().bikeDao().getBikeByPriceAndBrand(view.getDeclaredCashAmount(), item)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(bikes -> {
                    return Observable.just(bikes);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bikes -> {
                            view.showData(bikes);
                        },
                        throwable -> view.showError()));
    }

    @Override
    public void getBikesFilterByColor(String item) {

        view.showProgress();
        compositeDisposable.add(BikeApplication.getDb().bikeDao().getBikeByPriceAndColor(view.getDeclaredCashAmount(), item)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(bikes -> {
                    return Observable.just(bikes);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bikes -> {
                            view.showData(bikes);
                        },
                        throwable -> view.showError()));
    }


    @Override
    public void getBikesFilterByStatus(boolean item) {
        view.showProgress();
        compositeDisposable.add(BikeApplication.getDb().bikeDao().getBikeByPriceAndStatus(view.getDeclaredCashAmount(), item)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(bikes -> {
                    return Observable.just(bikes);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bikes -> {
                            view.showData(bikes);
                        },
                        throwable -> view.showError()));

    }

    @Override
    public void updateBikeTable(List<Integer> bikeId) {
        for (int i = 0; i < bikeId.size(); i++) {
            BikeApplication.getDb().bikeDao().setStatus(false, bikeId.get(i));
        }
    }

    @Override
    public void updateRentedBikeTable(List<Integer> bikeId, List<String> time) {

        AppDatabase db = BikeApplication.getDb();
        db.bikeDao().deleteAllRentedBikes();

        BikeRentByUser[] bikeRentByUsers = new BikeRentByUser[bikeId.size()];
        for (int i = 0; i < bikeId.size(); i++) {
            bikeRentByUsers[i] = new BikeRentByUser(bikeId.get(i), time.get(i), " ");
        }

        db.bikeDao().insertRentedBike(bikeRentByUsers);
    }

}