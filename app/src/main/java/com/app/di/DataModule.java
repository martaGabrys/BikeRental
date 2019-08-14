package com.app.di;

import android.content.Context;

import androidx.room.Room;

import com.app.ApplicationScope;
import com.app.dao.BikeDao;
import com.app.database.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @ApplicationScope
    AppDatabase provideAppDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "bike_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @ApplicationScope
    BikeDao provideBikeDao(AppDatabase appDatabase) {
        return appDatabase.bikeDao();
    }
}