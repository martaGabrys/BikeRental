package com.app.order;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {OrderModule.class})
public interface OrderComponent {
    void inject(OrderActivity orderActivity);
}