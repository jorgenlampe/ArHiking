package com.example.arhiking.fragments;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.arhiking.databinding.FragmentRegisterHikeBinding;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

import java.util.Date;

public class RegisterHikeFragment extends Fragment {

    private FragmentRegisterHikeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisterHikeViewModel registerHikeViewModel =
                new ViewModelProvider(this).get(RegisterHikeViewModel.class);

        binding = FragmentRegisterHikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRegisterHike;
        registerHikeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        registerHikeViewModel.startSensorService();

            registerHikeViewModel.getSensorData().observe(
                    getViewLifecycleOwner(), aFloat -> {
                        //todo... analysere/filtrere data fra sensorer
                        Log.i("Sensor change observed. OrientationX: ",
                                String.valueOf(aFloat[0]));
                        Log.i("Sensor change observed. OrientationY: ",
                                String.valueOf(aFloat[1]));
                        Log.i("Sensor change observed. OrientationZ: ",
                                String.valueOf(aFloat[2]));
                    });

            registerHikeViewModel.getAccelerationData().observe(
                    getViewLifecycleOwner(), accel -> {

              Log.i("Akselerasjon er: ", String.valueOf(accel));

                    }
            );
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}