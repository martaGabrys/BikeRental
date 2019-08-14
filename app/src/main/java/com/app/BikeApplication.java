package com.app;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.app.database.AppDatabase;
import com.app.di.AppComponent;
import com.app.di.AppModule;
import com.app.di.DaggerAppComponent;
import com.app.di.DataModule;

import timber.log.Timber;

public class BikeApplication extends Application {

        private static AppDatabase db;
        private AppComponent appComponent;

        @Override
        public void onCreate() {
            super.onCreate();


            if (BuildConfig.DEBUG) {
                Timber.plant(new Timber.DebugTree() {
                    @Override
                    protected String createStackElementTag(StackTraceElement element) {
                        return super.createStackElementTag(element) + " *** " + element.getLineNumber();
                    }
                });

            } else {
                Timber.plant(new CrashReportingTree());
            }

            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "bike_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .dataModule(new DataModule())
                    .build();

        }

        public static AppDatabase getDb() {
            return db;
        }

        public AppComponent getAppComponent() {
            return appComponent;
        }

        private static class CrashReportingTree extends Timber.Tree {
            @Override
            protected void log(int priority, String tag, @NonNull String message, Throwable t) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return;
                }
            }
        }
    }
