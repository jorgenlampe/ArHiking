package com.example.arhiking.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.arhiking.Services.SensorService;

public class RegisterHikeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<Float> sensorDataAcceleromaterData;
    public MutableLiveData<Float> sensorDataGeomagneticData;
    public MutableLiveData<Float> sensorDataGyroscopeData;
    public MutableLiveData<Float[]> sensorData;


    public RegisterHikeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");
    }

    public MutableLiveData<Float[]> getSensorData() {
        if (sensorData == null){
            sensorData = new MutableLiveData<Float[]>();
    }

        SensorService sensorService = new SensorService(
                getApplication().getApplicationContext());
        sensorService.listenToSensors();

        return sensorData;

    }

    public LiveData<String> getText() {
        return mText;
    }

  /*  public MutableLiveData<Float> getSensorAccelerometerData() {

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

    public MutableLiveData<Float> getSensorGyroscopeData() {

        if (sensorDataGyroscopeData == null) {
            sensorDataGyroscopeData = new MutableLiveData<>();
        }

        SensorService sensorService = new SensorService(
                getApplication().getApplicationContext());
        sensorService.getSensorGyroscopeDataFromSensorService();

        return sensorDataGyroscopeData;

    }*/
}
