package com.app.bike;

import com.app.dao.BikeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BikeModule {

    private BikeContract.View view;

    public BikeModule(BikeContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    BikeContract.Presenter provideBikePresenter(BikeDao bikeDao){
        return new BikePresenter(view, bikeDao);
    }
}
