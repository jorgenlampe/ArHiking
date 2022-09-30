package com.example.arhiking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.arhiking.R;
import com.github.mikephil.charting.charts.LineChart;

public class UserDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_data, container, false);

        // Inflate the layout for this fragment
        return view;
    }
}
