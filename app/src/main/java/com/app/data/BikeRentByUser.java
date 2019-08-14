package com.app.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = Bike.class,
        parentColumns = "id",
        childColumns = "bike_id"),
        tableName = "rentedBikes")

public class BikeRentByUser {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    public int id;

    @ColumnInfo(name = "bike_id", index = true)
    public int bikeId;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "price_day" )
    public float pricePerDayRent;

    @ColumnInfo(name = "status")
    public boolean isFreeToRent;

    @ColumnInfo(name = "timeStamp_of_renting")
    public String timeOfRenting;

    @ColumnInfo(name = "timeStamp_of_canceling")
    public String timeOfCanceling;


    public BikeRentByUser(int bikeId, String timeOfRenting, String timeOfCanceling) {
        this.bikeId = bikeId;
        this.timeOfRenting = timeOfRenting;
        this.timeOfCanceling = timeOfCanceling;
    }
}
