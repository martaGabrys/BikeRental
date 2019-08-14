package com.app.login;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private LoginContract.View view;


    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginContract.Presenter provideLoginPresenter() {
        return new LoginPresenter(view);
    }
}
