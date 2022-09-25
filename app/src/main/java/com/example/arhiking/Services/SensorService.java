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
import androidx.lifecycle.LiveData;

    public class SensorService extends Service implements SensorEventListener {

        private Context mContext;
        private SensorManager sensorManager;
        private Sensor geoMagneticSensor;
        private Sensor accelerometerSensor;

        public SensorService(Application application) {

        mContext = application.getApplicationContext();

        }

        @Override
        public void onCreate() {
            super.onCreate();
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            getDataFromSensors();
        }

        public LiveData<String> getDataFromSensors() {

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



        }

        public void stopListening(){
            sensorManager.unregisterListener(this);
        }




        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            int sensorType = sensorEvent.sensor.getType();
            float currentValue = sensorEvent.values[0];
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    Log.i("sensor:Accelerometer value: ", String.valueOf(currentValue));
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
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
