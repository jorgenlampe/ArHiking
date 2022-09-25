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

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.arhiking.Services.SensorService;

public class RegisterHikeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private SensorManager mSensorManager;
    public MutableLiveData<String> sensorData;
    private Sensor mSensorGeomagteticField;
    public SensorService sensorService;

    public RegisterHikeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");
        sensorService = new SensorService(application);
        
    }

    public LiveData<String> getText() {
        return mText;
    }

}