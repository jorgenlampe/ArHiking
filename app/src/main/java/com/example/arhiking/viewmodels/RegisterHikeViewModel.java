package com.example.arhiking.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.arhiking.Data.AccelerometerDao;
import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.GeomagneticDao;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.Models.AccelerometerData;
import com.example.arhiking.Models.GeoPointsFromHikeActivity;
import com.example.arhiking.Models.GeomagneticSensorData;
import com.example.arhiking.Models.HikeGeoPoint;
import com.google.android.gms.maps.model.LatLng;


import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterHikeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<float[]> sensorData; //orientation
    public MutableLiveData<Float> accelerationData;
    public MutableLiveData<Integer> trackingStatus;
    public MutableLiveData<GeoPoint> currentLocation;
    //todo livedata for turId???





    public RegisterHikeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");

    }

    public MutableLiveData<Integer> getTrackingStatus() {
        if (trackingStatus == null) {
            trackingStatus = new MutableLiveData<>();
        }

        return trackingStatus;
        //1 - start, 2 - pause, 3 - stop
    }

    public MutableLiveData<float[]> getSensorData() {
        if (sensorData == null) {
            sensorData = new MutableLiveData<float[]>();
        }

        return sensorData;

    }

    public MutableLiveData<GeoPoint> getCurrentLocation() {
        if (currentLocation == null) {
            currentLocation = new MutableLiveData<>();
        }

        return currentLocation;

    }


    public MutableLiveData<Float> getAccelerationData() {
        if (accelerationData == null) {
            accelerationData = new MutableLiveData<Float>();
        }

        return accelerationData;

    }

    public LiveData<String> getText() {

        return mText;
    }


    public void startSensorService() {

        SensorService sensorService = new SensorService(
                getApplication().getApplicationContext());
        sensorService.listenToSensors();
    }

    public class SensorService extends AppCompatActivity implements SensorEventListener {

        private static final int REQUEST_CODE = 999;
        LocationManager locationManager;
        LatLng latLng;

        private final SensorManager sensorManager;
        private final Sensor geoMagneticSensor;
        private final Sensor accelerometerSensor;
        private final Sensor gyroscopeSensor;
        private final float[] accelerometerReading = new float[3];
        private final float[] magnetometerReading = new float[3];

        private final float[] rotationMatrix = new float[9];
        private final float[] orientationAngles = new float[3];

        Context _context;

        HikeActivityDao hikeActivityDao;

        AppDatabase db;

        public SensorService(Context context) {

            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            geoMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            _context = context;
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            setUpLocationManager();

            db = Room.databaseBuilder(context,
            AppDatabase.class, "database-name").allowMainThreadQueries().build();

            hikeActivityDao = db.hikeActivityDao();

        }




        private void setUpLocationManager() {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            Log.i("location updated: ",
                                    String.valueOf(location.getAccuracy()));
                            latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            Log.i("location information: ",
                                    getLocationInfo(latLng));
                         //   saveToDatabase(latLng);

                        }
                    });
        }

        private void saveToDatabase(LatLng latLng) {
            GeoPoint point = new GeoPoint(latLng.latitude, latLng.longitude);
            HikeGeoPoint hikeGeoPoint = new HikeGeoPoint();
            hikeGeoPoint.geoPoint = point;
            //hikeGeoPoint.hike_activity_id = //todo få tak i id på gjeldende tur
            db.geoPointsDao().insertAll(hikeGeoPoint);
        }

        private String getLocationInfo(LatLng latLng) {
            Geocoder geoCoder = new Geocoder(_context, Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocation(latLng.latitude,
                        latLng.longitude, 1);
                Address adr = addresses.get(0);
                return adr.getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
///\todo get last Known Location
            return "";
        }


        public void stopListening() {
            sensorManager.unregisterListener(this);

        }

         public void calculateAcceleration(float[] values){

            Float acceleration = (float)Math.sqrt(
                    values[0]*values[0]+values[1]*values[1]+values[2]*
                    values[2]);

            if (acceleration > 0.5f)  //filter
             getAccelerationData().setValue(acceleration);


         }

        public void updateOrientationAngles() {
            // Update rotation matrix, which is needed to update orientation angles.
            SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelerometerReading, magnetometerReading);
            // "rotationMatrix" now has up-to-date information.

            float[] orientation = SensorManager.getOrientation(rotationMatrix, orientationAngles);
            getSensorData().setValue(orientation);
            // "orientationAngles" now has up-to-date information.
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

           /* AppDatabase db = Room.databaseBuilder(_context,
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();
*/
            AccelerometerDao accelerometerDao = db.accelerometerDao();
            GeomagneticDao geomagneticDao = db.geomagneticDao();

            Date date = new Date();

            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(sensorEvent.values, 0, accelerometerReading,
                        0, accelerometerReading.length);
                Log.i("acceleromater sensor changed. value_x: ", String.valueOf(accelerometerReading[0]));
                Log.i("acceleromater sensor changed. value_y: ", String.valueOf(accelerometerReading[1]));
                Log.i("acceleromater sensor changed. value_z: ", String.valueOf(accelerometerReading[2]));

                AccelerometerData accelerometerData = new AccelerometerData();
                accelerometerData.timeRegistered = date.getTime();
                //todo koble til aktuell tur... accelerometerData.hike_activity_id
                accelerometerData.xValue = accelerometerReading[0];
                accelerometerData.yValue = accelerometerReading[1];
                accelerometerData.zValue = accelerometerReading[2];

                //lagrer til dataase todo bare lagre hvert 10. sekund?
                accelerometerDao.insertAll(accelerometerData);

                //beregner akselerasjon
               /* float[] testValues = new float[3];
                testValues[0] = (float)1.26;
                testValues[1] = (float)1.26;
                testValues[2] = (float)1.26;
                calculateAcceleration(testValues);
*/
                calculateAcceleration(sensorEvent.values);



            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(sensorEvent.values, 0, magnetometerReading,
                        0, magnetometerReading.length);
                Log.i("magnetometer sensor changed. value_x: ", String.valueOf(magnetometerReading[0]));
                Log.i("magnetometer sensor changed. value_y: ", String.valueOf(magnetometerReading[1]));
                Log.i("magnetometer sensor changed. value_z: ", String.valueOf(magnetometerReading[2]));

                GeomagneticSensorData geomagneticData = new GeomagneticSensorData();
                geomagneticData.timeRegistered = date.getTime();
                //todo koble til aktuell tur... geomagneticData.hike_activity_id
                geomagneticData.xValue = magnetometerReading[0];
                geomagneticData.yValue = magnetometerReading[1];
                geomagneticData.zValue = magnetometerReading[2];

                geomagneticDao.insertAll(geomagneticData);

            }


            calculateOrientationFromSensors(magnetometerReading, accelerometerReading);

        }

        public void listenToSensors() {
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

        public void calculateOrientationFromSensors(float[] magnetometerValues, float[] acceleromaterValues) {

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

    }

