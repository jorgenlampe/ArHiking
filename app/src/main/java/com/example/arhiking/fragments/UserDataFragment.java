package com.example.arhiking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentUserDataBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserDataFragment extends Fragment {

    private FragmentUserDataBinding binding;

    BarChart barChartHikesPerMonth;
    BarData barDataHikesPerMonth;
    BarDataSet barDataSetHikesPerMonth;
    ArrayList barHikesPerMonthEntriesArrayList;
    ArrayList<String> monthLabels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barChartHikesPerMonth = root.findViewById(R.id.barCharUserData);
        getHikesPerMonthDataEntries();

        barDataSetHikesPerMonth = new BarDataSet(barHikesPerMonthEntriesArrayList, "Hikes");
        barDataHikesPerMonth = new BarData(barDataSetHikesPerMonth);

        barChartHikesPerMonth.setData(barDataHikesPerMonth);
        barChartHikesPerMonth.getDescription().setText("The total number of hikes in each month this year");
        barDataSetHikesPerMonth.setColors(ColorTemplate.COLORFUL_COLORS);
        barChartHikesPerMonth.animateY(1000);

        return root;
    }

    private void getHikesPerMonthDataEntries(){
        barHikesPerMonthEntriesArrayList = new ArrayList<>();

        barHikesPerMonthEntriesArrayList.add(new BarEntry(1, 1));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(2, 3));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(3, 1));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(4, 5));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(5, 4));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(6, 8));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(7, 6));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(8, 7));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(9, 5));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(10, 3));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(11, 0));
        barHikesPerMonthEntriesArrayList.add(new BarEntry(12, 2));

        // TODO find out and fix x to show month strings
/*        monthLabels = new ArrayList<>();
        monthLabels.add("January");
        monthLabels.add("February");
        monthLabels.add("March");
        monthLabels.add("April");
        monthLabels.add("May");
        monthLabels.add("June");
        monthLabels.add("July");
        monthLabels.add("August");
        monthLabels.add("September");
        monthLabels.add("October");
        monthLabels.add("November");
        monthLabels.add("December");*/
    }



    public double getHighPassFilteredAccelerometerData(float x, float y, float z) {
         Float[] gravity = {0.0f, 0.0f, 0.0f};
         final float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
        gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
        gravity[2] = alpha * gravity[2] + (1 - alpha) * z;

        float fx = x - gravity[0];
        float fy = y - gravity[1];
        float fz = z - gravity[2];

        double accelerationFilteredValue = Math.sqrt(fx * fx + fy * fy + fz * fz);
        return accelerationFilteredValue;
    }
}