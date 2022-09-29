package com.example.arhiking.Services;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;

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

    public void getSensorGyroscopeDataFromSensorService() {

        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

        public void stopListening(){
            sensorManager.unregisterListener(this);

        }

    public void updateOrientationAngles() {  //observere denne i stedet....
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        // "orientationAngles" now has up-to-date information.
    }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(sensorEvent.values, 0, accelerometerReading,
                        0, accelerometerReading.length);
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(sensorEvent.values, 0, magnetometerReading,
                        0, magnetometerReading.length);
            }

            //todo endre databasen til å ta imot Float[]...?
/*
            int sensorType = sensorEvent.sensor.getType();
            float currentValue = sensorEvent.values[0]; //sende tre verdier til calculation...
            Date date = new Date();
            Long time = date.getTime();
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    try {
                      //lagrer i database
                        hikeActivityDao.addAccelerometerSensorTimeData(1, time);
                        hikeActivityDao.addAccelerometerSensorData(1, currentValue);

                        //oppdaterer LiveData-objekt
                        //todo fungerer ikke
                        RegisterHikeViewModel model = new RegisterHikeViewModel
                                (getApplication());
                        model.getSensorAccelerometerData().postValue(
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
                        model.getSensorGeomagneticData().postValue(
                                Float.valueOf(currentValue));



                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i("sensor:Geomagnetic field value: ", String.valueOf(currentValue));
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    try {
                        //lagrer i database
                        hikeActivityDao.addGyroscopeTimeData(1, time);
                        hikeActivityDao.addGyroscopeSensorData(1, currentValue);

                        //oppdaterer LiveData-objekt
                        RegisterHikeViewModel model = new RegisterHikeViewModel
                                (getApplication());
                        model.getSensorGyroscopeData().postValue(
                                Float.valueOf(currentValue));


                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.i("sensor:Gyroscope field value: ", String.valueOf(currentValue));
                    break;
                default:

                    Log.i("sensor:Unknown sensor type: ", String.valueOf(currentValue));



            }
*/
        }

    void calculateOrientationFromSensors(float[] magnetometerValues, float[] acceleromaterValues){

        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                acceleromaterValues, magnetometerValues);

        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        //todo.... hvordan lese inn data... og hva skal metode returnere?
    }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


}
