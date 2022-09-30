package com.example.arhiking.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.arhiking.Services.SensorService;

public class RegisterHikeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<Float> sensorDataAcceleromaterData;
    public MutableLiveData<Float> sensorDataGeomagneticData;
    public MutableLiveData<Float> sensorDataGyroscopeData;
    public MutableLiveData<float[]> sensorData;


    public RegisterHikeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");
        mText.hasObservers();
        if (mText.hasObservers()) {
            Log.i("observers ok", "yes");
        }
        if (mText.hasActiveObservers()) {
            Log.i("active observers ok", "yes");
        }


    }


    public MutableLiveData<float[]> getSensorData() {
        if (sensorData == null){
            sensorData = new MutableLiveData<float[]>();
    }

        if (sensorData.hasObservers())
            Log.i("observer ok?", "yes");

        if (sensorData.hasActiveObservers())
            Log.i("active observer ok?", "yes");

        return sensorData;

    }

    public LiveData<String> getText() {
        return mText;
    }


    public void startSensorService() {

        try {
            SensorService sensorService = new SensorService(
                    getApplication().getApplicationContext());
            sensorService.listenToSensors();

        } catch(Exception e){
            e.getMessage();
        }
    }

}
