package com.example.arhiking.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.UserDao;
import com.example.arhiking.Models.User;

import java.util.List;

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