package com.example.arhiking.Services;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

public class SensorService extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor geoMagneticSensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    HikeActivityDao hikeActivityDao;

    public SensorService() {

    }

    public SensorService(Context context) {

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        try {
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();

            hikeActivityDao = db.hikeActivityDao();

        } catch (Exception e) {
            e.getMessage();
        }
    }

        public void stopListening(){
            sensorManager.unregisterListener(this);

        }

    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        float[] orientation = SensorManager.getOrientation(rotationMatrix, orientationAngles);

        RegisterHikeViewModel model = new RegisterHikeViewModel
                (getApplication());
        model.getSensorData().setValue(orientation);


        // "orientationAngles" now has up-to-date information.
    }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(sensorEvent.values, 0, accelerometerReading,
                        0, accelerometerReading.length);
                Log.i("acceleromater sensor changed. value_x: ", String.valueOf(accelerometerReading[0]));
                Log.i("acceleromater sensor changed. value_y: ", String.valueOf(accelerometerReading[1]));
                Log.i("acceleromater sensor changed. value_z: ", String.valueOf(accelerometerReading[2]));
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(sensorEvent.values, 0, magnetometerReading,
                        0, magnetometerReading.length);
                Log.i("magnetometer sensor changed. value_x: ", String.valueOf(magnetometerReading[0]));
                Log.i("magnetometer sensor changed. value_y: ", String.valueOf(magnetometerReading[1]));
                Log.i("magnetometer sensor changed. value_z: ", String.valueOf(magnetometerReading[2]));
            }

            calculateOrientationFromSensors(magnetometerReading, accelerometerReading);


        }

        public void listenToSensors(){
            if (geoMagneticSensor != null) {
                sensorManager.registerListener(this, geoMagneticSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            if (accelerometerSensor != null) {
                sensorManager.registerListener(this, accelerometerSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            if (gyroscopeSensor != null) {
                sensorManager.registerListener(this, gyroscopeSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

        }

    public void calculateOrientationFromSensors(float[] magnetometerValues, float[] acceleromaterValues){

        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                acceleromaterValues, magnetometerValues);

        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        updateOrientationAngles();

    }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


}
