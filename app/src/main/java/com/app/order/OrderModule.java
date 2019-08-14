package com.app.order;

import com.app.dao.BikeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderModule {

    private OrderContract.View view;

    public OrderModule(OrderContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    OrderContract.Presenter provideOrderPresenter(BikeDao bikeDao) {
        return new OrderPresenter(view, bikeDao);
    }
}
