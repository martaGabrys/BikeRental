package com.app.di;

import com.app.ApplicationScope;
import com.app.bike.BikeComponent;
import com.app.bike.BikeModule;
import com.app.login.LoginComponent;
import com.app.login.LoginModule;
import com.app.order.OrderComponent;
import com.app.order.OrderModule;
import com.app.wallet.WalletComponent;
import com.app.wallet.WalletModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    LoginComponent plus (LoginModule loginModule);
    WalletComponent plus (WalletModule walletModule);
    BikeComponent plus (BikeModule bikeModule);
    OrderComponent plus (OrderModule orderModule);

}