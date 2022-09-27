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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.Services.SensorService;
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

        /*
        try {

//todo feiler her....

            registerHikeViewModel.getSensorAccelerometerData().observe(
                    getViewLifecycleOwner(), new Observer<Float>() {
                        @Override
                        public void onChanged(Float aFloat) {
                            //todo.....
                            Log.i("sensor changed - value: ", aFloat.toString());
                        }
                    });

        } catch(Exception e)
        {

        }
*/

        return root;  //todo feiler her...
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SensorService service = new SensorService(getContext());
        service.stopListening();
        binding = null;
    }
}