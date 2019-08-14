package com.app.login;

import javax.inject.Singleton;
import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}