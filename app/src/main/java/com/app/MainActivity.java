package com.app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.app.dao.BikeDao;
import com.app.data.Bike;
import com.app.database.AppDatabase;
import com.app.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BikeApplication.getDb().bikeDao().deleteAllRentedBikes();
        BikeApplication.getDb().bikeDao().deleteALLBikes();
        addBike(BikeApplication.getDb());

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finishscreen();
            }
        };

        timerRun(task1, 2500);
    }



    private static void addBike(AppDatabase db) {
        BikeDao bikeDao = db.bikeDao();
        bikeDao.insertBike(setBikeData());
    }

    private static Bike[] setBikeData(){
        Bike [] bikeRepo = new Bike[15];
        bikeRepo[0] = new Bike("Giant", "red", (float) 9.50, true);
        bikeRepo[1] = new Bike("Giant", "blue", (float) 12, true);
        bikeRepo[2] = new Bike("Giant", "green", (float) 8, true);
        bikeRepo[3] = new Bike("Giant", "black", (float) 7.25, true);
        bikeRepo[4] = new Bike("Giant", "yellow", (float) 15, false);
        bikeRepo[5] = new Bike("Merida", "red", (float) 11.50, true);
        bikeRepo[6] = new Bike("Merida", "blue", (float) 13, true);
        bikeRepo[7] = new Bike("Merida", "green", (float) 13, false);
        bikeRepo[8] = new Bike("Merida", "yellow", (float) 9, true);
        bikeRepo[9] = new Bike("Merida", "black", (float) 10, false);
        bikeRepo[10] = new Bike("Maxim", "red", (float) 10, true);
        bikeRepo[11] = new Bike("Maxim", "blue", (float) 12.5, true);
        bikeRepo[12] = new Bike("Maxim", "green", (float) 12, true);
        bikeRepo[13] = new Bike("Maxim", "yellow", (float) 12, false);
        bikeRepo[14] = new Bike("Maxim", "black", (float) 13, true);
        return bikeRepo;
    }


    private void timerRun(TimerTask task, int delayed ) {
        Timer t = new Timer();
        t.schedule(task, delayed);
    }

    private void finishscreen() {
        this.finish();
    }

}

