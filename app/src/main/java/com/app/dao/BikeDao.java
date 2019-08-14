package com.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.data.Bike;
import com.app.data.BikeRentByUser;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BikeDao {

    @Query("SELECT * FROM bikes")
    Single<List<Bike>> getAllBikes();

    @Query("SELECT*FROM bikes WHERE price_day <= :price")
    Single<List<Bike>> getBikeByPrice(float price);

    @Insert
    void insertBike(Bike... bikes);

    @Update
    void updateBike(Bike... bikes);

    @Query("UPDATE bikes SET status = :status WHERE id = :id ")
    void setStatus(boolean status, int id);

    @Delete
    void deleteBike(Bike... bikes);

    @Query("DELETE FROM bikes")
    void deleteALLBikes();

    @Query("SELECT MIN(price_day) FROM bikes")
    float getMinValueOfPrice();

    @Query("SELECT DISTINCT color FROM bikes")
    String[] getAllColor();

    @Query("SELECT DISTINCT brand FROM bikes")
    String[] getAllBrand();

    @Query("SELECT DISTINCT status FROM bikes")
    boolean[] getAllStatus();

    @Query("SELECT*FROM bikes WHERE price_day <= :price AND brand = :brand")
    Single<List<Bike>> getBikeByPriceAndBrand(float price, String brand);

    @Query("SELECT*FROM bikes WHERE price_day <= :price AND color = :color")
    Single<List<Bike>> getBikeByPriceAndColor(float price, String color);

    @Query("SELECT*FROM bikes WHERE price_day <= :price AND status = :status")
    Single<List<Bike>> getBikeByPriceAndStatus(float price, boolean status);

    @Insert
    void insertRentedBike(BikeRentByUser... bikes);

    @Update
    void updateRentedBike(BikeRentByUser... bikes);

    @Delete
    void deleteRentedBike(BikeRentByUser... bikes);

    @Query("DELETE FROM rentedBikes")
    void deleteAllRentedBikes();

    @Query("SELECT * FROM rentedBikes " +
            "INNER JOIN bikes ON bikes.id = rentedBikes.bike_id")
    Single<List<BikeRentByUser>> getAllRentedBikes();

    @Query("SELECT * FROM rentedBikes")
    public abstract List <BikeRentByUser> getRentedBikeList();

    @Query("UPDATE rentedBikes SET timeStamp_of_canceling = :time ")
    void setTimeOfCanceling(String time);

    @Query("SELECT price_day FROM bikes WHERE id = :id")
    float getPriceDay (String id);

}
