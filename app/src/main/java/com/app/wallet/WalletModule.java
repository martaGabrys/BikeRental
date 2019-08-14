package com.app.wallet;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WalletModule {

    private WalletContract.View view;

    public WalletModule(WalletContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    WalletContract.Presenter provideWalletPresenter(){
        return new WalletPresenter(view);
    }
}
