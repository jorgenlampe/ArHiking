package com.example.arhiking.fragments;

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

import com.example.arhiking.databinding.FragmentRegisterHikeBinding;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

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

        registerHikeViewModel.getSensorData().observe(getViewLifecycleOwner(), s -> {

            Log.d("RPMFragment", this.toString());

        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}