package com.app.di;


import android.content.Context;
import com.app.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    Context provideContext(){return context;}
}
