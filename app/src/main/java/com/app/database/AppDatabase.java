package com.app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.app.dao.BikeDao;
import com.app.data.Bike;
import com.app.data.BikeRentByUser;

@Database(entities = {Bike.class, BikeRentByUser.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BikeDao bikeDao();

}
