package com.example.arhiking.Services;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.Models.HikeActivity;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

import java.util.Date;

public class SensorService extends Service implements SensorEventListener {

        private SensorManager sensorManager;
        private Sensor geoMagneticSensor;
        private Sensor accelerometerSensor;
        private AppDatabase db;
        private HikeActivityDao dao;

        public SensorService(){

        }

        public SensorService(Application context) {

          /*  db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();//allowMainThreadQueries().build();
            //          addMigrations(MIGRATION_3_4).allowMainThreadQueries().build();

            dao = db.hikeActivityDao();*/
            try {
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } catch (Exception e) {
                Log.i("feil:::", e.getMessage());
            }
        }

        @Override  //todo register in manifest??? or extend service and register service....
        public void onCreate() {

            //todo onCreate not called....
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void getSensorAccelerometerDataFromSensorService() {

        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

       /* public LiveData<String> getDataFromSensors() {

        startListening();

        return null; //todo returnere data fra sensor... og lagre i DB

        }

        public void startListening(){

            if (geoMagneticSensor != null) {
                sensorManager.registerListener(this, geoMagneticSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            if (accelerometerSensor != null) {
                sensorManager.registerListener(this, accelerometerSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }



        }*/

        public void stopListening(){
            sensorManager.unregisterListener(this);
        }



/*
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
*/
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            int sensorType = sensorEvent.sensor.getType();
            float currentValue = sensorEvent.values[0];
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    dao.addAccelerometerSensorTimeData(1, new Date());
                    Log.i("sensor:Accelerometer value: ", String.valueOf(currentValue));
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    Log.i("sensor:Geomagnetic field value: ", String.valueOf(currentValue));
                    break;
                default:
                    //test....

                    //sensorFromAccelerometerValue = currentValue;
                    Log.i("sensor:Unknown sensor type: ", String.valueOf(currentValue));

                    RegisterHikeViewModel model = new RegisterHikeViewModel
                            (getApplication());
                    model.getSensorAccelerometerData().setValue(
                            currentValue);




            }





            //todo lagre til database


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
