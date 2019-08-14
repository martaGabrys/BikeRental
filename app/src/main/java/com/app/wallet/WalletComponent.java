package com.app.wallet;


import javax.inject.Singleton;
import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {WalletModule.class})
public interface WalletComponent {
    void inject(WalletActivity walletActivity);
}
