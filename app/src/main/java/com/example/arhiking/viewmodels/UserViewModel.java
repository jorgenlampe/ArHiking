package com.example.arhiking.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.arhiking.Data.AppDatabase;

public class UserViewModel extends ViewModel {

    private AppDatabase database;
    private final MutableLiveData<String> mText;

    public UserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}