package com.example.arhiking.viewmodels;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.arhiking.Services.SensorService;

public class RegisterHikeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<Float> sensorDataAcceleromaterData;
    public MutableLiveData<Float> sensorDataGeomagneticData;



    public RegisterHikeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Float> getSensorAccelerometerData() {

        if (sensorDataAcceleromaterData == null) {
            sensorDataAcceleromaterData = new MutableLiveData<>();
        }

            SensorService sensorService = new SensorService(
                    getApplication().getApplicationContext());
            sensorService.getSensorAccelerometerDataFromSensorService();

            //sjekker om det er aktive observers...
        Boolean active = sensorDataAcceleromaterData.hasActiveObservers();

        return sensorDataAcceleromaterData;

    }

    public MutableLiveData<Float> getSensorGeomagneticData() {

        if (sensorDataGeomagneticData == null) {
            sensorDataGeomagneticData = new MutableLiveData<>();
        }

        SensorService sensorService = new SensorService(
                getApplication().getApplicationContext());
        sensorService.getSensorGeomagneticDataFromSensorService();

        return sensorDataGeomagneticData;

    }
}
