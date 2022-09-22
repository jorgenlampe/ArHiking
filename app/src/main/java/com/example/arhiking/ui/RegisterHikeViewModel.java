package com.example.arhiking.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterHikeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RegisterHikeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is register hike fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}