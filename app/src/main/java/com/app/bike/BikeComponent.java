package com.app.bike;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {BikeModule.class})
public interface BikeComponent {
    void inject(BikeActivity bikeActivity);
}