package com.example.arhiking.Services;

import android.app.Activity;
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

public class SensorService extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor geoMagneticSensor;
    private Sensor accelerometerSensor;

    HikeActivityDao hikeActivityDao;

    public SensorService() {

    }

    public SensorService(Context context) {

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        try {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();

            hikeActivityDao = db.hikeActivityDao();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void getSensorAccelerometerDataFromSensorService() {

        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public void getSensorGeomagneticDataFromSensorService() {

        if (geoMagneticSensor != null) {
            sensorManager.registerListener(this, geoMagneticSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

        public void stopListening(){
            sensorManager.unregisterListener(this);

        }


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            int sensorType = sensorEvent.sensor.getType();
            float currentValue = sensorEvent.values[0];
            Date date = new Date();
            Long time = date.getTime();
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    try {
                      //lagrer i database
                        hikeActivityDao.addAccelerometerSensorTimeData(1, time);
                        hikeActivityDao.addAccelerometerSensorData(1, currentValue);

                        //oppdaterer LiveData-objekt
                        RegisterHikeViewModel model = new RegisterHikeViewModel
                                (getApplication());
                        model.getSensorAccelerometerData().setValue(
                                Float.valueOf(currentValue));

                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i("sensor:Accelerometer value: ", String.valueOf(currentValue));
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    try {
                        //lagrer i database
                        hikeActivityDao.addGeomagneticSensorTimeData(1, time);
                        hikeActivityDao.addGeomagneticSensorData(1, currentValue);

                        //oppdaterer LiveData-objekt
                        RegisterHikeViewModel model = new RegisterHikeViewModel
                                (getApplication());
                        model.getSensorGeomagneticData().setValue(
                                Float.valueOf(currentValue));

                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i("sensor:Geomagnetic field value: ", String.valueOf(currentValue));
                    break;
                default:

                    Log.i("sensor:Unknown sensor type: ", String.valueOf(currentValue));



            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


}
