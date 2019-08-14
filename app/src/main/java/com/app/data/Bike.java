package com.app.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity (tableName = "bikes")
public class Bike {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "price_day" )
    public float pricePerDayRent;

    @ColumnInfo(name = "status")
    public boolean isFreeToRent;

    public Bike(String brand, String color, float pricePerDayRent, boolean isFreeToRent) {
        this.brand = brand;
        this.color = color;
        this.pricePerDayRent = pricePerDayRent;
        this.isFreeToRent = isFreeToRent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPricePerDayRent() {
        return pricePerDayRent;
    }

    public void setPricePerDayRent(float pricePerDayRent) {
        this.pricePerDayRent = pricePerDayRent;
    }

    public boolean isFreeToRent() {
        return isFreeToRent;
    }

    public void setFreeToRent(boolean freeToRent) {
        isFreeToRent = freeToRent;
    }

}
